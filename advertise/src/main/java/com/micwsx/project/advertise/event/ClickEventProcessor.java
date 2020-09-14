package com.micwsx.project.advertise.event;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * click 处理类,用户点击菜单事件
 */
@Component("CLICK_EVENT")
public class ClickEventProcessor extends MessageProcessor {


//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[FromUser]]></FromUserName>
//      <CreateTime>123456789</CreateTime>
//      <MsgType><![CDATA[event]]></MsgType>
//      <Event><![CDATA[CLICK]]></Event>
//      <EventKey><![CDATA[EVENTKEY]]></EventKey>
//    </xml>

    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String eventKey = message.get("EventKey");
        // 功能扩展 TODO：根据项目自定义(Click)菜单事件，若有Click菜单则对应实现EventKey事件
        switch (eventKey) {
            case "":
                break;
            default:
                break;
        }
    }
}
