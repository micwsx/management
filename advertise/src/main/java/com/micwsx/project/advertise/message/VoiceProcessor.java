package com.micwsx.project.advertise.message;

import com.micwsx.project.advertise.event.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("VOICE_MESSAGE")
public class VoiceProcessor extends MessageProcessor {


//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[fromUser]]></FromUserName>
//      <CreateTime>1357290913</CreateTime>
//      <MsgType><![CDATA[voice]]></MsgType>
//      <MediaId><![CDATA[media_id]]></MediaId>
//      <Format><![CDATA[Format]]></Format>
//      <MsgId>1234567890123456</MsgId>
//    </xml>


    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String format = message.get("Format");
        String msgId = message.get("MsgId");
        //  向用户返回语音
        wechatContext.sendVoiceMessage(openId, msgId);

    }
}
