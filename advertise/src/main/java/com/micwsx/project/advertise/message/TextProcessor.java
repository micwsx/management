package com.micwsx.project.advertise.message;

import com.micwsx.project.advertise.domain.ClockIn;
import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.event.MessageProcessor;
import com.micwsx.project.advertise.service.imp.ClockInService;
import com.micwsx.project.advertise.utility.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 处理用户发送消息
 * TODO: 待引用MQ实现消息发送
 */
@Component("TEXT_MESSAGE")
public class TextProcessor extends MessageProcessor {


    @Autowired
    private ClockInService clockInService;
//    <xml>
//      <ToUserName><![CDATA[toUser]]></ToUserName>
//      <FromUserName><![CDATA[fromUser]]></FromUserName>
//      <CreateTime>1348831860</CreateTime>
//      <MsgType><![CDATA[text]]></MsgType>
//      <Content><![CDATA[this is a test]]></Content>
//      <MsgId>1234567890123456</MsgId>
//    </xml>

    @Override
    public void processMessage(Map<String, String> message) {
        String openId = message.get("FromUserName");
        String content = message.get("Content");
        String reply = "";
        // TODO：支持关键字与用户互动; 如：近期活动
        switch (KeyWord.valueOf(content)) {
            case 人数统计:
                reply = summary();
                break;
            case 近期活动:
                break;
            case 签到:
                reply = clockIn(openId);
            default:
                break;
        }
        // 发送消息
        if (!StringUtils.isEmpty(reply)) {
            wechatContext.sendTextMessage(openId, reply);
        }
    }

    /**
     * 人数统计消息回复
     *
     * @return
     */
    private String summary() {
        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger todayNum = new AtomicInteger();
        AtomicInteger followedNum = new AtomicInteger();
        AtomicInteger yesterdayNum = new AtomicInteger();

        List<Member> members = memberService.findAll();
        stringBuilder.append("总人数：" + members.size()).append("\r\n");
        members.parallelStream().forEach(m -> {
            if (m.isSubscribe()) {
                followedNum.incrementAndGet();
                if (DateUtil.isToday(m.getSubscribeTime()))
                    todayNum.incrementAndGet();
                if (DateUtil.isYesterday(m.getSubscribeTime()))
                    yesterdayNum.incrementAndGet();
            }
        });
        stringBuilder.append("总关注人数: " + followedNum.get()).append("\r\n")
                .append("今日关注人数: " + todayNum.get()).append("\r\n")
                .append("昨日关注人数: " + yesterdayNum.get());

//        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }


    /**
     * 签到消息回复
     * @param openId
     * @return
     */
    private String clockIn(String openId) {

        String message="";
        // 用户连接签到天数
        int consecutive=1;
        ClockIn yesterdayRecord = clockInService.checkIn(openId);
        if (yesterdayRecord!=null){
            consecutive=yesterdayRecord.getAttend()+consecutive;
        }
        ClockIn clockIn=new ClockIn();
        clockIn.setMemberid(openId);
        clockIn.setAttend(consecutive);
        int result=clockInService.add(clockIn);
        if (result>0){
            message= "连续签到"+consecutive+"天";
        }
        return  message;
    }

}
