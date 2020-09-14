package com.micwsx.project.advertise.dao;

import com.micwsx.project.advertise.domain.MemberCondition;
import com.micwsx.project.advertise.domain.Participant;
import com.micwsx.project.advertise.domain.ParticipantInfo;
import com.micwsx.project.advertise.domain.ParticipantItem;

import java.util.List;

public interface ParticipantMapper {

    List<ParticipantItem> selectAllSum();

    /**
     * 根据会议id,会员id,会员名名，支付状态查询
     * @param condition
     * @return
     */
    List<ParticipantInfo> selectCriteria(MemberCondition condition);


    Participant selectById(String id);

    List<Participant> selectAll();

    int add(Participant participant);

    int addBatch(List<Participant> l);

    int delete(String id);

    int deleteBatch(String[] ids);

    int update(Participant participant);

}
