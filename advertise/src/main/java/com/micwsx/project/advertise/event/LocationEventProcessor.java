package com.micwsx.project.advertise.event;

import com.micwsx.project.advertise.domain.Member;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * location 处理类，更新用户上一次地理位置（如果用户开户上报地理功能）
 */
@Component("LOCATION_EVENT")
public class LocationEventProcessor extends MessageProcessor {

// <xml>
//  <ToUserName><![CDATA[toUser]]></ToUserName>
//  <FromUserName><![CDATA[fromUser]]></FromUserName>
//  <CreateTime>123456789</CreateTime>
//  <MsgType><![CDATA[event]]></MsgType>
//  <Event><![CDATA[LOCATION]]></Event>
//  <Latitude>23.137466</Latitude>
//  <Longitude>113.352425</Longitude>
//  <Precision>119.385040</Precision>
//</xml>

    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        Member member = memberService.get(openId);
        member.setLatitude(message.get("Latitude"));
        member.setLongitude(message.get("Longitude"));
        member.setPrecision(message.get("Precision"));
        memberService.update(member);
        logger.info("处理用户[" + openId + "]上报地理功能");
    }
}
