package com.micwsx.project.advertise.service.imp;

import com.micwsx.project.advertise.dao.ConferenceMapper;
import com.micwsx.project.advertise.domain.Conference;
import com.micwsx.project.advertise.domain.ConferenceItem;
import com.micwsx.project.advertise.service.TConferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConferenceService implements TConferenceService {


    @Autowired
    private ConferenceMapper conferenceMapper;

    /**
     * 根据会议id获取会议及人员数据
     * @param id
     * @return
     */
    @Override
    public Conference findById(Integer id) {
        return conferenceMapper.selectById(id);
    }

    /**
     * 会议编号和举办时间，查询会议记录（不包含参加人员数据）
     * @param map
     * @return
     */
    @Override
    public List<Conference> findCriteria(Map map) {
        return conferenceMapper.selectAll(map);
    }

    /**
     *获取未开始的会议与参数人关系数据
     * @return
     */
    @Override
    public  List<ConferenceItem>  findOnline() {
        List<ConferenceItem> conferenceList = conferenceMapper.selectValid();
        return conferenceList;
    }

    /**
     *根据会员id获取会员参加会议按会议记录 按时间降序
     * @param memberId
     * @return
     */
    @Override
    public List<Conference> getConferencesByMemberId(String memberId) {
        return conferenceMapper.selectConferences(memberId);
    }


    @Override
    public int add(Conference conference) {
        return conferenceMapper.add(conference);
    }

    @Override
    public int delete(Integer id) {
        return conferenceMapper.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return conferenceMapper.deleteBatch(ids);
    }

    @Override
    public int update(Conference conference) {
        return conferenceMapper.update(conference);
    }
}

