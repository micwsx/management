package com.micwsx.project.advertise.event;

import com.micwsx.project.advertise.common.QRCodeContext;
import com.micwsx.project.advertise.common.WechatContext;
import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.service.TMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * subscribe 处理类
 * 处理关注消息(包括扫描带参数的二维码)
 */
@Component("SUBSCRIBE_EVENT")
public class SubscribeEventProcessor extends MessageProcessor {

    @Autowired
    private QRCodeContext qrCodeContext;

    public TMemberService getMemberService() {
        return memberService;
    }

    //<xml>
    //    <ToUserName><![CDATA[gh_f4634c7744c9]]></ToUserName>
    //    <FromUserName><![CDATA[os7enjs4r1LvOifRcJLL6R-eXs2k]]></FromUserName>
    //    <CreateTime>1546432853</CreateTime>
    //    <MsgType><![CDATA[event]]></MsgType>
    //    <Event><![CDATA[subscribe]]></Event>
    //    <EventKey><![CDATA[qrscene_os7enjoshdMPTJIKWPpWcGXDiEIo]]></EventKey>
    //    <Ticket><![CDATA[gQER8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyLVhxTUZJUFRhRGUxMU0xS05zY1AAAgTwryxcAwSAUQEA]]></Ticket>
    //</xml>

    @Override
    public void processMessage(Map<String, String> message) {
        int result = 0;
        String openId = message.get("FromUserName");
        String eventKey = message.get("EventKey");
        String parentId = eventKey.contains("qrscene_") ? eventKey.replace("qrscene_", "") : "";
        // 判断用户是否已存在
        Optional<Member> optionalMember = Optional.ofNullable(memberService.get(openId));
        // 获取用户数据
        Member latestUserInfo = wechatContext.getUserInfo(openId);
        //  关注过，重新获取用户数据，更新用户数据
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setSubscribe(latestUserInfo.isSubscribe());
            member.setName(latestUserInfo.getName());// nickName->name
            member.setNickName(latestUserInfo.getNickName());
            member.setGender(latestUserInfo.isGender());
            member.setLang(latestUserInfo.getLang());
            member.setCity(latestUserInfo.getCity());
            member.setProvince(latestUserInfo.getProvince());
            member.setCountry(latestUserInfo.getCountry());
            member.setHeadImgurl(latestUserInfo.getHeadImgurl());
            member.setSubscribeTime(latestUserInfo.getSubscribeTime());
            member.setUnionId(latestUserInfo.getUnionId());
            member.setRemark(latestUserInfo.getRemark());
            member.setGroupId(latestUserInfo.getGroupId());
            member.setTagidlist(latestUserInfo.getTagidlist());
            member.setSubscribeScene(latestUserInfo.getSubscribeScene());
            member.setQrScene(latestUserInfo.getQrScene());
            member.setQrSceneStr(latestUserInfo.getQrSceneStr());
            result = memberService.update(member);
            // 发送关注者消息
            wechatContext.sendTextMessage(openId, "你已关注，谢谢！");

        } else {
            // 新用户
            // 设置推荐人Id
            latestUserInfo.setRefId(parentId);
            // 生成个人推荐码
            String qrcodeUrl=qrCodeContext.getQRCodeSrc(openId, 0);
            latestUserInfo.setSharedQRcodeUrl(qrcodeUrl);
            // 没关注，直接添加用户
            result = memberService.add(latestUserInfo);
            // 发送关注者消息
            wechatContext.sendTextMessage(openId, WechatContext.WELCOME_MESSAGE);

            if (result > 0) {
                // 异常处理推荐人逻辑
                new Thread(() -> {
                    Optional<String> optionalParentOpenId = Optional.ofNullable(parentId);
                    if (optionalParentOpenId.isPresent()) {
                        // 存在推荐人
                        // TODO: 是否有后续逻辑??
                        // 发送推荐成功消息
                        wechatContext.sendTextMessage(openId, WechatContext.RECOMMEND_MESSAGE + " [" + latestUserInfo.getNickName() + "]");
                    }
                }).start();
            } else {
                logger.error("关注事件处理消息报文失败：　" + message.toString());
            }
        }
    }
}
