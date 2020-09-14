package com.micwsx.project.advertise.service.imp;

import com.micwsx.project.advertise.dao.ClockInMapper;
import com.micwsx.project.advertise.domain.ClockIn;
import com.micwsx.project.advertise.service.IClockInService;
import com.micwsx.project.advertise.dao.ClockInMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Michael
 * @create 7/31/2020 3:37 PM
 */
@Service
public class ClockInService implements IClockInService {

    @Autowired
    private ClockInMapper clockInMapper;

    /**
     * 获取昨天的签到记录
     * @param memberId
     * @return
     */
    @Override
    public ClockIn checkIn(String memberId) {
        return clockInMapper.selectYesterdayClockIn(memberId);
    }

    @Override
    public int add(ClockIn clockIn) {
        return clockInMapper.insertSelective(clockIn);
    }
}
