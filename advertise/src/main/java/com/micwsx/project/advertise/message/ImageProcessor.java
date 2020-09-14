package com.micwsx.project.advertise.message;

import com.micwsx.project.advertise.event.MessageProcessor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("IMAGE_MESSAGE")
public class ImageProcessor extends MessageProcessor {

//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[fromUser]]></FromUserName>
//      <CreateTime>1348831860</CreateTime>
//      <MsgType><![CDATA[image]]></MsgType>
//      <PicUrl><![CDATA[this is a url]]></PicUrl>
//      <MediaId><![CDATA[media_id]]></MediaId>
//      <MsgId>1234567890123456</MsgId>
//    </xml>


    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String picUrl = message.get("PicUrl");
        String mediaId = message.get("MediaId");
        String msgId = message.get("MsgId");

        //  向用户返回发送图片
        wechatContext.sendImageMessage(openId, msgId);

    }
}
