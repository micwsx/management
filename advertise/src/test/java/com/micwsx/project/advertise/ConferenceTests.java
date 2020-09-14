package com.micwsx.project.advertise;

import com.micwsx.project.advertise.dao.ConferenceMapper;
import com.micwsx.project.advertise.domain.Conference;
import com.micwsx.project.advertise.utility.DateUtil;
import com.micwsx.project.advertise.dao.ConferenceMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@MapperScan("com.micwsx.project.advertise.dao")
class ConferenceTests {

    @Autowired
    private ConferenceMapper conferenceMapper;


    @Test
    void select(){

        Conference conference=new Conference();
        conference.setId(5);
        conference.setReleased(false);
//        System.out.println(conference);
        conference.setSeatNum(34);
        int update = conferenceMapper.update(conference);
        System.out.println(update);

//        List<Conference> conferences = conferenceMapper.selectConferences("M0001");
//        System.out.println(conferences);
//        List<Conference> conferenceList = conferenceMapper.selectAll();
//        System.out.println(conferenceList);

//        Conference conference = conferenceMapper.selectById(1);
//        List<ParticipantInfo> participants = conference.getParticipants();
//        System.out.println(conference);


    }


    @Test
    void contextLoads() {

        addConference();
    }

    private void addConference() {
        Conference conference=new Conference();
        conference.setId(3);
        conference.setSerialNumber("20200718XXXXMIBGAWG1");
        conference.setBanner("/site/images/topic.jpg");
        conference.setTopic("时尚汇");
        conference.setBrief("时尚汇精英，全球服饰产业链全聚于此");
        conference.setIntroduction("一场饭局，畅聊七小时，深交十多位，时尚与服饰大咖");
        conference.setLocation("上海松江富悦大酒店");
        conference.setSiteLink("");//场地图片
        conference.setSiteBrief("");//场地介绍
        conference.setHoster("Jim");
        conference.setHosterLink("/site/images/head.jpeg");//
        conference.setHosterBrief("Thnkout全球设计师");
        conference.setGuest("一龙");
        conference.setGuestLink("/site/images/head.jpeg");
        conference.setGuestBrief("行业top1");
        conference.setFee(99.99F);
        conference.setStartTime(DateUtil.getDate(2020,10,1,10,0,0));
        conference.setEndTime(DateUtil.getDate(2020,10,1,12,0,0));
        conference.setReleased(true);
        conference.setSeatNum(50);
        conference.setRemark("first open");
        conferenceMapper.add(conference);

    }

}
