package com.micwsx.project.advertise.service;


import com.micwsx.project.advertise.domain.ClockIn;

/**
 * @author Michael
 * @create 7/31/2020 3:36 PM
 */
public interface IClockInService {

    /**
     * 获取昨天的签到记录
     * @param memberId
     * @return
     */
    ClockIn checkIn(String memberId);

    int add(ClockIn clockIn);
}
