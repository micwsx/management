package com.micwsx.project.advertise.message;

import com.micwsx.project.advertise.event.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("LOCATION_MESSAGE")
public class LocationProcessor extends MessageProcessor {

//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[fromUser]]></FromUserName>
//      <CreateTime>1351776360</CreateTime>
//      <MsgType><![CDATA[location]]></MsgType>
//      <Location_X>23.134521</Location_X>
//      <Location_Y>113.358803</Location_Y>
//      <Scale>20</Scale>
//      <Label><![CDATA[位置信息]]></Label>
//      <MsgId>1234567890123456</MsgId>
//    </xml>

    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String label = message.get("Label");
        wechatContext.sendTextMessage(openId,label);
    }
}
