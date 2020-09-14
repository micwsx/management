package com.micwsx.project.advertise.controller;


import com.micwsx.project.advertise.common.WechatContext;
import com.micwsx.project.advertise.event.ApplicationContextProvider;
import com.micwsx.project.advertise.event.MessageProcessor;
import com.micwsx.project.advertise.utility.Dom4JUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/entry")
public class EntryController extends BaseController {

    @Autowired
    private WechatContext wechatContext;

    @RequestMapping("/process")
    public String process(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Start");
        if (request.getMethod().equals("GET")) {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            boolean flag = wechatContext.verifyMessageFromWechat(signature, nonce, timestamp);
            return flag ? echostr : "Invalid Message[From Wechat]";
        } else {
            StringBuilder xmlBody = new StringBuilder();
            try {
                BufferedReader reader = request.getReader();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    xmlBody.append(line);
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //打印微信发送消息报文
            logger.info("Wechat message: "+xmlBody.toString());
            // 解析xml消息报文
            Map<String, String> map = Dom4JUtil.parseXML2Map(xmlBody.toString());
            String msgType = map.get("MsgType");
            MessageProcessor processor = null;
            if (msgType.equals("event")) {
                // 处理事件eventName：subscribe,unsubscribe,SCAN,LOCATION,CLICK,VIEW
                processor = (MessageProcessor) ApplicationContextProvider.getBean(map.get("Event").toUpperCase() + "_EVENT");
            } else {
                // 处理消息msgType:text,image,voice,video,shortvideo,location,link
                processor = (MessageProcessor) ApplicationContextProvider.getBean(msgType.toUpperCase() + "_MESSAGE");
            }
            if (processor==null){
                logger.error("Wechat异常消息: "+xmlBody.toString());
                throw new UnsupportedOperationException("没有消息对应的处理器");
            }
            processor.processMessage(map);
            logger.info("End");
            // 回复微信
            return "success";
        }
    }


}
