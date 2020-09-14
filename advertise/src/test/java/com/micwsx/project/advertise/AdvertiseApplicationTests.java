package com.micwsx.project.advertise;

import com.micwsx.project.advertise.dao.MemberMapper;
import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.domain.MemberHierarchy;
import com.micwsx.project.advertise.event.ApplicationContextProvider;
import com.micwsx.project.advertise.event.SubscribeEventProcessor;
import com.micwsx.project.advertise.utility.DateUtil;
import com.micwsx.project.advertise.viewmodel.PersonalCenter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class AdvertiseApplicationTests {


    @Autowired
    private MemberMapper memberMapper;


    @Test
    void statistic() {

        StringBuilder stringBuilder = new StringBuilder();
        AtomicInteger todayNum = new AtomicInteger();
        AtomicInteger followedNum = new AtomicInteger();
        AtomicInteger yesterdayNum = new AtomicInteger();

        List<Member> members = memberMapper.selectAll();
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
        System.out.println(stringBuilder.toString());

    }

    @Test
    void testUpdate() {
//        System.out.println(memberMapper.selectAll());
        Map<String, Object> map = new HashMap<>();
        map.put("subscribe", true);
//        map.put("idList","'M0003','M0004'");
        int i = memberMapper.batchUpdateSubscribe(map);
        System.out.println(i);
    }

    @Test
    void testMessage() {
        SubscribeEventProcessor subscribe_event = (SubscribeEventProcessor) ApplicationContextProvider.getBean("SUBSCRIBE_EVENT");
        System.out.println(subscribe_event.getMemberService());
    }

    @Test
    void testProcedure() {

        List<MemberHierarchy> list = memberMapper.getChildTable("M0004");
        PersonalCenter personalCenter = new PersonalCenter();
        MemberHierarchy owner = list.get(0);
        personalCenter.setId(owner.getId());
        personalCenter.setHeadImgurl(owner.getHeadImgUrl());
        personalCenter.setName(owner.getName());
        personalCenter.setNickName(owner.getNickName());
        personalCenter.setSubscribeTime(owner.getSubscribeTime());
        personalCenter.setRefId(owner.getRefId());
        personalCenter.setParentName(owner.getParentName());
        personalCenter.setParentNickName(owner.getParentNickName());
        personalCenter.setParentHeadImgurl(owner.getParentHeadImgUrl());
        //等待前端获取
//        personalCenter.setOpenId();
        personalCenter.setTotal(list.size() - 1);

        list.stream().filter(m -> m.getLevel() > 0).forEach(m -> {
            if (DateUtil.isToday(m.getSubscribeTime())) {
                int todayNum = personalCenter.getTodayNum() + 1;
                personalCenter.setTodayNum(todayNum);
            }
            if (DateUtil.isYesterday(m.getSubscribeTime())) {
                int yesterdayNum = personalCenter.getYesterdayNum() + 1;
                personalCenter.setYesterdayNum(yesterdayNum);
            }
            if (m.getLevel() == 1)
                personalCenter.getDirectMembers().add(m);
            else if (m.getLevel() > 1)
                personalCenter.getInDirectMembers().add(m);
        });

        System.out.println(personalCenter);

//        System.out.println(list.size());
//        System.out.println(list.get(0).getRefId());
//        System.out.println(Optional.ofNullable(list.get(0).getRefId()).orElse("无"));

//        Map<String,String> map=new HashMap<>();
//        map.put("id", "M0004");
//        memberMapper.getParentList(map);
//         if (map.containsKey("parentList")){
//             System.out.println(map.get("parentList"));
//         }
    }


    @Test
    void contextLoads() {
        addMember();
//        System.out.println(memberMapper.selectAll().size());
//        Member member = new Member("M0001");
//        member.setRefId("M0002");
//        member.setName("Michael Wu");
//        member.setMobile("1345654345");
//        member.setEmail("8128566@qq.com");
//        member.setActive(false);
//        member.setRemark("Test");

//        System.out.println(memberMapper.update(member));

        // System.out.println(memberMapper.selectAll());

//        System.out.println(memberMapper.delete("M0001"));

    }

    private void addMember() {
        Member member = new Member("M0001");
        member.setHeadImgurl("/site/images/head.jpeg");
        member.setName("Michael Wu");
        member.setCountry("China");
        member.setCity("Hubei");
        member.setProvince("Hubei");
        member.setLang("ZH");
        member.setGender(true);
        member.setSubscribeTime(new Date());
        member.setEmail("Michael.wu@computop.com");
        member.setNickName("Michael");
        member.setSharedQRcodeUrl("/site/images/offical_qrcode.jpg");
        System.out.println(memberMapper.add(member));


        Member member2 = new Member("M0002");
        member2.setRefId("M0001");
        member2.setHeadImgurl("/site/images/head.jpeg");
        member2.setName("Jack Li");
        member2.setCountry("China");
        member2.setGender(true);
        member2.setSubscribeTime(new Date());
        member2.setEmail("Jack@computop.com");
        member2.setNickName("Jack");
        member2.setSharedQRcodeUrl("/site/images/offical_qrcode.jpg");
        System.out.println(memberMapper.add(member2));

        Member member3 = new Member("M0003");
        member3.setRefId("M0001");
        member3.setHeadImgurl("/site/images/head.jpeg");
        member3.setName("Alice");
        member3.setCountry("China");
        member3.setGender(true);
        member3.setSubscribeTime(new Date());
        member3.setEmail("Alice@computop.com");
        member3.setNickName("Alice");
        member3.setSharedQRcodeUrl("/site/images/offical_qrcode.jpg");
        System.out.println(memberMapper.add(member3));

        Member member4 = new Member("M0004");
        member4.setRefId("M0003");
        member4.setName("Jason");
        member4.setHeadImgurl("/site/images/head.jpeg");
        member4.setCountry("China");
        member4.setGender(true);
        member4.setSubscribeTime(new Date());
        member4.setEmail("Jason@computop.com");
        member4.setNickName("Jason");
        member4.setSharedQRcodeUrl("/site/images/offical_qrcode.jpg");
        System.out.println(memberMapper.add(member4));


    }

}
