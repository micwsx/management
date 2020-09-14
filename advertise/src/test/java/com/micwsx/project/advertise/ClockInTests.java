package com.micwsx.project.advertise;

import com.micwsx.project.advertise.dao.ClockInMapper;
import com.micwsx.project.advertise.domain.ClockIn;
import com.micwsx.project.advertise.dao.ClockInMapper;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@MapperScan("com.micwsx.project.advertise.dao")
class ClockInTests {

    @Autowired
    private ClockInMapper clockInMapper;

    @Test
    void contextLoads() {

        int consecutive=1;
        ClockIn yesterdayRecord = clockInMapper.selectYesterdayClockIn("M0001");
        if (yesterdayRecord!=null){
            consecutive=yesterdayRecord.getAttend()+consecutive;
        }

        System.out.println(consecutive);

//        ClockIn clockIn=new ClockIn();
//        clockIn.setMemberid("M0001");
//        clockIn.setAttend(1);
//
//        int re=clockInMapper.insertSelective(clockIn);
//        System.out.println(re);


    }



}
