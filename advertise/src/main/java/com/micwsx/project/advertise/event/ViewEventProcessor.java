package com.micwsx.project.advertise.event;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * view菜单点击事件处理
 */
@Component("VIEW_EVENT")
public class ViewEventProcessor extends MessageProcessor {

//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[FromUser]]></FromUserName>
//      <CreateTime>123456789</CreateTime>
//      <MsgType><![CDATA[event]]></MsgType>
//      <Event><![CDATA[VIEW]]></Event>
//      <EventKey><![CDATA[www.qq.com]]></EventKey>
//    </xml>


    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String eventKey = message.get("EventKey");
        // 功能扩展 TODO: 可以记录点击量

    }
}
