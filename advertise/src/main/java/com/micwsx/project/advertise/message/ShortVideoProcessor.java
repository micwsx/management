package com.micwsx.project.advertise.message;

import com.micwsx.project.advertise.event.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("SHORTVIDEO_MESSAGE")
public class ShortVideoProcessor extends MessageProcessor {

//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[fromUser]]></FromUserName>
//      <CreateTime>1357290913</CreateTime>
//      <MsgType><![CDATA[shortvideo]]></MsgType>
//      <MediaId><![CDATA[media_id]]></MediaId>
//      <ThumbMediaId><![CDATA[thumb_media_id]]></ThumbMediaId>
//      <MsgId>1234567890123456</MsgId>
//    </xml>

    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String mediaId = message.get("MediaId");
        wechatContext.sendVideoMessage(openId, mediaId,"发送小视频","返回用户小视频");
    }
}
