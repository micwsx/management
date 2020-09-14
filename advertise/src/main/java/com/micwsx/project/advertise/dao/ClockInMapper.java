package com.micwsx.project.advertise.dao;

import com.micwsx.project.advertise.domain.ClockIn;

public interface ClockInMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ClockIn record);

    int insertSelective(ClockIn record);

    ClockIn selectYesterdayClockIn(String memberId);

    ClockIn selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ClockIn record);

    int updateByPrimaryKey(ClockIn record);
}