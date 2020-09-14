package com.micwsx.project.advertise.common;

import com.alibaba.fastjson.JSONObject;
import com.micwsx.project.advertise.utility.HttpUtility;
import org.springframework.stereotype.Component;

@Component
public class ServiceCenter extends Context{

    /**
     * 客服接口-发消息
     *
     * @param openId
     * @param message
     * @return
     */
    public boolean sendTextMessage(String openId, String message) {
        String jsonBody = "{\"touser\":\"" + openId + "\",\"msgtype\":\"text\",\"text\":{ \"content\":\"" + message + "\"}}";
        return sendMessage(openId, jsonBody);
    }

    /**
     * 客服接口-发图片消息
     *
     * @param openId
     * @param mediaId
     * @return
     */
    public boolean sendImageMessage(String openId, String mediaId) {
        String jsonBody = "{\"touser\":\"" + openId + "\",\"msgtype\":\"image\",\"image\":{ \"media_id\":\"" + mediaId + "\"}}";
        return sendMessage(openId, jsonBody);
    }

    /**
     * 客服接口-发语音消息
     *
     * @param openId
     * @param mediaId
     * @return
     */
    public boolean sendVoiceMessage(String openId, String mediaId) {
        String jsonBody = "{\"touser\":\"" + openId + "\",\"msgtype\":\"voice\",\"voice\":{ \"media_id\":\"" + mediaId + "\"}}";
        return sendMessage(openId, jsonBody);
    }


    /**
     * 客服接口-发视频消息
     *
     * @param openId
     * @param mediaId
     * @return
     */
    public boolean sendVideoMessage(String openId, String mediaId, String title, String description) {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{")
                .append("\"touser\":\"" + openId + "\",")
                .append("\"msgtype\":\"video\",")
                .append("video\":{")
                .append("\"media_id\":\"" + mediaId + "\",")
                .append("\"thumb_media_id\":\"" + mediaId + "\",")
                .append("\"title\":\"" + title + "\",")
                .append("\"description\":\"" + description + "\"")
                .append("}")
                .append("}");
        return sendMessage(openId, jsonBody.toString());
    }

    /**
     * 客服接口-发音乐消息
     * @param openId
     * @param title
     * @param description
     * @param musicUrl
     * @param hqmusicUrl
     * @param thumbMedaiId
     * @return
     */
    public boolean sendMusicMessage(String openId, String title, String description, String musicUrl, String hqmusicUrl, String thumbMedaiId) {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{")
                .append("\"touser\":\"" + openId + "\",")
                .append("\"msgtype\":\"music\",")
                .append("music\":{")
                .append("\"title\":\"" + title + "\",")
                .append("\"description\":\"" + description + "\",")
                .append("\"musicurl\":\"" + musicUrl + "\",")
                .append("\"hqmusicurl\":\"" + hqmusicUrl + "\",")
                .append("\"thumb_media_id\":\"" + thumbMedaiId + "\"")
                .append("}")
                .append("}");
        return sendMessage(openId, jsonBody.toString());
    }

    /**
     * 客服接口-发图文消息(点击跳转到外链)
     *
     * @param openId
     * @param title
     * @param description
     * @param url
     * @param picurl
     * @return
     */
    public boolean sendRichMessage(String openId, String title, String description, String url, String picurl) {
        StringBuilder jsonBody = new StringBuilder();
        jsonBody.append("{")
                .append("\"touser\":\"" + openId + "\",")
                .append("\"msgtype\":\"news\",")
                .append("news\":{")
                .append(" \"articles\": [")
                .append("(")
                .append(" \"title\":\"" + title + "\",")
                .append(" \"description\":\"" + description + "\",")
                .append(" \"url\":\"" + url + "\",")
                .append(" \"picurl\":\"" + picurl + "\",")
                .append(")")
                .append("]")
                .append("}")
                .append("}");
        return sendMessage(openId, jsonBody.toString());
    }

    /**
     * 客服接口-发图文消息(点击跳转到图文消息页面)
     * @param openId
     * @param mediaId
     * @return
     */
    public boolean sendRichMessage(String openId,String mediaId){
        String jsonBody = "{\"touser\":\"" + openId + "\",\"msgtype\":\"mpnews\",\"mpnews\":{ \"media_id\":\"" + mediaId + "\"}}";
        return sendMessage(openId, jsonBody.toString());
    }

    private boolean sendMessage(String openId, String jsonBody) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + getAccessToken().getToken();
        String responseMsg = HttpUtility.post(url, jsonBody);
        JSONObject jsonObject = JSONObject.parseObject(responseMsg);
        // {"errcode":0,"errmsg":"ok"}
        return jsonObject.getInteger("errcode") == 0;
    }

}
