package com.micwsx.project.advertise.service.imp;

import com.micwsx.project.advertise.dao.ParticipantMapper;
import com.micwsx.project.advertise.domain.MemberCondition;
import com.micwsx.project.advertise.domain.Participant;
import com.micwsx.project.advertise.domain.ParticipantInfo;
import com.micwsx.project.advertise.domain.ParticipantItem;
import com.micwsx.project.advertise.service.TParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService implements TParticipantService {

    @Autowired
    private ParticipantMapper participantMapper;

    /**
     * 获取参加会议基本情况
     * @return
     */
    @Override
    public List<ParticipantItem> selectAllSum() {
        return participantMapper.selectAllSum();
    }

    @Override
    public List<ParticipantInfo> selectCriteria(MemberCondition condition) {
        return participantMapper.selectCriteria(condition);
    }

    @Override
    public Participant selectById(String id) {
        return participantMapper.selectById(id);
    }

    @Override
    public List<Participant> selectAll() {
        return participantMapper.selectAll();
    }

    @Override
   public int add(Participant participant){
        // 向participant表中插入一条记录。
        return participantMapper.add(participant);
    }

    @Override
    public int addBatch(List<Participant> l) {
        return participantMapper.addBatch(l);
    }

    @Override
    public int delete(String id) {
        return participantMapper.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return participantMapper.deleteBatch(ids);
    }

    @Override
    public int update(Participant participant) {
        return participantMapper.update(participant);
    }
}
