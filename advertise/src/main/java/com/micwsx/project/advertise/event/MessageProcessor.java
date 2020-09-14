package com.micwsx.project.advertise.event;

import com.micwsx.project.advertise.common.WechatContext;
import com.micwsx.project.advertise.service.TMemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public abstract class MessageProcessor {

    @Autowired
    protected WechatContext wechatContext;

    @Autowired
    protected TMemberService memberService;

    protected Logger logger= LoggerFactory.getLogger(this.getClass());

    public abstract void processMessage(Map<String, String> message);
}
