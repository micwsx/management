package com.micwsx.project.advertise.message;

import com.micwsx.project.advertise.event.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("LINK_MESSAGE")
public class LinkProcessor extends MessageProcessor {

//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[fromUser]]></FromUserName>
//      <CreateTime>1351776360</CreateTime>
//      <MsgType><![CDATA[link]]></MsgType>
//      <Title><![CDATA[公众平台官网链接]]></Title>
//      <Description><![CDATA[公众平台官网链接]]></Description>
//      <Url><![CDATA[url]]></Url>
//      <MsgId>1234567890123456</MsgId>
//    </xml>


    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String url = message.get("Url");
        wechatContext.sendTextMessage(openId, "发送链接：" + url);

    }
}
