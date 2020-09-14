package com.micwsx.project.advertise.event;

import com.micwsx.project.advertise.domain.Member;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * unsubscribe 处理类
 */
@Component("UNSUBSCRIBE_EVENT")
public class UnSubscribeEventProcessor extends MessageProcessor{

    @Override
    public void processMessage(Map<String, String> message) {
        // 更新用户订阅状态
        String openId = message.get("FromUserName");
        Member member =new Member(openId);
        member.setSubscribe(false);
        memberService.update(member);
    }
}
