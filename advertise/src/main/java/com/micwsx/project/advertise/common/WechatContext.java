package com.micwsx.project.advertise.common;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.utility.FileUtil;
import com.micwsx.project.advertise.utility.HttpUtility;
import com.micwsx.project.advertise.utility.Util;
import com.micwsx.project.advertise.viewmodel.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 调用微信服务类
 */
@Component
public class WechatContext extends Context {

    private static final String PAYMENT_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String WELCOME_MESSAGE = "谢谢关注！";
    public static final String RECOMMEND_MESSAGE = "成功推荐";

    @Autowired
    private MenuContext menuContext;
    @Autowired
    private QRCodeContext qrCodeContext;

    @Autowired
    private ServiceCenter serviceCenter;

    public boolean initalMenu(String host) {
        return menuContext.createMenu(host);
    }

    /**
     * 验证微信消息
     *
     * @return
     */
    public boolean verifyMessageFromWechat(String signature, String nonce, String timestamp) {
        String[] arrays = {nonce, Credential.TOKEN, timestamp};
        String plainText = Arrays.asList(arrays).stream().sorted(String::compareTo).reduce("", String::concat);
        String hashValue = sha1(plainText);
        return signature.equals(hashValue);
    }


    public String authorizedUrl(String redirectUrl, String state) {
        String authorizeUrl = null;
        try {
            authorizeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
                    "appid=" + Credential.APPID +
                    "&redirect_uri=" + URLEncoder.encode(redirectUrl, "utf-8") +
                    "&response_type=code&scope=snsapi_base&state=" + state + "#wechat_redirect";
            //string urlTemplate = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type={2}&scope={3}&state={4}#wechat_redirect";
            //string authorizeUrl = string.Format(urlTemplate, appID, System.Web.HttpUtility.UrlEncode(redirectUrl), "code", "snsapi_userinfo", state);
            return authorizeUrl;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return authorizeUrl;
    }


    /**
     * 根据code获取用户openId
     *
     * @param code
     * @return
     */
    public String getOpenIdByCode(String code) {
        String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Credential.APPID +
                "&secret=" + Credential.APPSECRET +
                "&code=" + code + "&grant_type=authorization_code";
        String responseMsg = HttpUtility.get(accessTokenUrl);
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);

        logger.info("getOpenIdByCode request url:" + accessTokenUrl + "\r\n response:" + jsonObject);
        String openid = Optional.ofNullable(jsonObject.getString("openid")).orElse("");
        return openid;
    }

    /**
     * 获取服务商公众号二维码
     *
     * @return
     */
    public String getOfficeQRCode() {
        File fileInDoc = FileUtil.getFileInDoc(QRCodeContext.OFFICAL_QRCODE);
        if (fileInDoc.exists()) {
            // 获取二维码的相对路径
            String relativePath = FileUtil.getRelativePath(fileInDoc);
            logger.info(relativePath);
            return relativePath;
        }
        // 不存在则向微信发送请求
        String qrCodeSrc = qrCodeContext.getQRCodeSrc("Public Account QRCode", 0);
        logger.info(qrCodeSrc);
        return qrCodeSrc;
    }

    /**
     * 获取用户基本信息
     *
     * @param openId
     * @return
     */
    public Member getUserInfo(String openId) {
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + getAccessToken().getToken() +
                "&openid=" + openId + "&lang=zh_CN";
        String responseMsg = HttpUtility.get(url);
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);

        Member member = new Member(openId);
        member.setSubscribe(jsonObject.getInteger("subscribe") == 1);
        member.setName(jsonObject.getString("nickname"));// nickName->name
        member.setNickName(jsonObject.getString("nickname"));
        member.setGender(jsonObject.getInteger("sex") == 1);
        member.setLang(jsonObject.getString("language"));
        member.setCity(jsonObject.getString("city"));
        member.setProvince(jsonObject.getString("province"));
        member.setCountry(jsonObject.getString("country"));
        member.setHeadImgurl(jsonObject.getString("headimgurl"));
        member.setSubscribeTime(jsonObject.getDate("subscribe_time"));
        member.setUnionId(jsonObject.getString("unionid"));
        member.setRemark(jsonObject.getString("remark"));
        member.setGroupId(jsonObject.getString("groupid"));
        member.setTagidlist(jsonObject.getString("tagid_list"));
        member.setSubscribeScene(jsonObject.getString("subscribe_scene"));
        member.setQrScene(jsonObject.getString("qr_scene"));
        member.setQrSceneStr(jsonObject.getString("qr_scene_str"));

        return member;
    }

    public boolean sendTextMessage(String openId, String message) {
        return serviceCenter.sendTextMessage(openId, message);
    }

    public boolean sendImageMessage(String openId, String message) {
        return serviceCenter.sendImageMessage(openId, message);
    }

    public boolean sendImageMessage(String openId, String title, String description, String musicUrl, String hqmusicUrl, String thumbMedaiId) {
        return serviceCenter.sendMusicMessage(openId, title,description,musicUrl,hqmusicUrl,thumbMedaiId);
    }

    public boolean sendRichMessage(String openId, String title, String description, String url, String picurl) {
        return serviceCenter.sendRichMessage(openId, title,description,url,picurl);
    }

    public boolean sendRichMessage(String openId,String mediaId) {
        return serviceCenter.sendRichMessage(openId, mediaId);
    }

    public boolean sendVoiceMessage(String openId,String mediaId) {
        return serviceCenter.sendVoiceMessage(openId, mediaId);
    }

    public boolean sendVideoMessage(String openId, String mediaId, String title, String description) {
        return serviceCenter.sendVideoMessage(openId, mediaId,title,description);
    }

    /**
     * 调用微信JSAPI支付接口，返回prepayid预支付id号.
     */
    public String jsapiPay(Map<String, String> param) throws BusinessException {
        Map<String, String> map = new HashMap<>();
        map.put("appid", Credential.PAY_APPID);
        map.put("mch_id", Credential.PAY_MCHID);
        map.put("nonce_str", UUID.randomUUID().toString().replace("-", ""));
        map.put("sign_type", "MD5");
        map.put("body", map.get("description"));//会议编号
        map.put("attach", param.get("id"));//participant id号附加参数，微信返回
        map.put("out_trade_no", param.get("id"));//商户订单号
        map.put("total_fee", String.valueOf(param.get("amount")));//金额
        map.put("spbill_create_ip", param.get("ip"));//ip
        map.put("notify_url", param.get("notifyUrl"));
        map.put("trade_type", "JSAPI");
        String sign = Util.wechatSign(map);
        map.put("sign", sign);
        // 转化为XML数据格式
        String requestMsg = Util.mapToXml(map);
        String responseMsg = HttpUtility.postWithXml(PAYMENT_URL, requestMsg);
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);
        String return_code = jsonObject.getString("return_code");
        String result_code = jsonObject.getString("result_code");
        String resultMsg;

        if (return_code.equals("SUCCESS")) {
            if (result_code.equals("SUCCESS")) {
//               jsonObject.getString("trade_type");
//                jsonObject.getString("code_url");
                String prepay_id = jsonObject.getString("prepay_id");
                String nonceStr = UUID.randomUUID().toString().replace("-", "");
                Map<String, String> prepayMap = new HashMap<>();
                prepayMap.put("appId", Credential.PAY_APPID);
                prepayMap.put("timeStamp", String.valueOf(System.currentTimeMillis()));
                prepayMap.put("nonceStr", nonceStr);
                prepayMap.put("package", "prepay_id=" + prepay_id);
                prepayMap.put("signType", "MD5");
                String signString = Util.wechatSign(prepayMap);
                prepayMap.put("paySign", signString);
                String jsonParam = JSONUtils.toJSONString(prepayMap);
                logger.info("JSAPI微信请求json字符串：" + jsonParam);
                return jsonParam;
            } else {
                resultMsg = jsonObject.getString("err_code_des");
            }
        } else {
            resultMsg = jsonObject.getString("return_msg");
        }
        throw new BusinessException(resultMsg);
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("c", "Michael");
        map.put("b", "Jack");
        String sign = map.entrySet().stream()
                .filter(kv -> !StringUtils.isEmpty(kv.getValue()) && !kv.getKey().equals("sign"))
                .sorted(Map.Entry.comparingByKey())
                .map(kv -> kv.getKey() + "=" + kv.getValue())
                .collect(Collectors.joining("&"));

        System.out.println(sign);

//        WechatContext wechatContext = new WechatContext();
//        AccessToken accessToken = wechatContext.getAccessToken();
//        System.out.println(accessToken);
    }


}
