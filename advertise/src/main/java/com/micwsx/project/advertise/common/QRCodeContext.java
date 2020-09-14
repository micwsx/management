package com.micwsx.project.advertise.common;

import com.alibaba.fastjson.JSONObject;
import com.micwsx.project.advertise.utility.HttpUtility;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

@Component
public class QRCodeContext extends Context {

    public static final String OFFICAL_QRCODE="offical_qrcode.jpg";

    /**
     * @param sceneParam:二维码场景参数，扫描关注后，微信则返回此参数
     * @param expirySeconds：有值则创建临时QRCode，无值则创建永久QRCode
     * @return
     */
    private String getTicket(String sceneParam, int expirySeconds) {
        String json = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneParam + "\"}}}";
        if (expirySeconds > 0)
            json = "{\"expire_seconds\": " + expirySeconds + ",\"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneParam + "\"}}}";
        String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + getAccessToken().getToken();
        String responseMsg = HttpUtility.post(url, json);
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);
        return Optional.ofNullable(jsonObject.getString("ticket")).orElse("");
    }


    /**
     * 根据参数生成(永久或临时)src
     *
     * @param sceneParam:二维码场景参数，扫描关注后，微信则返回此参数
     * @param expirySeconds：有值则创建临时QRCode，无值则创建永久QRCode
     * @return
     */
    public String getQRCodeSrc(String sceneParam, int expirySeconds) {
        String ticket = getTicket(sceneParam, expirySeconds);
        String url = "";
        try {
            url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + URLEncoder.encode(ticket, "utf-8");
            // 下载二维码到doc目录下
            HttpUtility.donwloadToDoc(url, OFFICAL_QRCODE);
            return url;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url;
    }
}
