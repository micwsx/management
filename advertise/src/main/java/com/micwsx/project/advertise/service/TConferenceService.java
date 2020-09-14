package com.micwsx.project.advertise.service;

import com.micwsx.project.advertise.domain.Conference;
import com.micwsx.project.advertise.domain.ConferenceItem;

import java.util.List;
import java.util.Map;

public interface TConferenceService {

    /**
     * 根据会议id获取会议及人员数据
     * @param id
     * @return
     */
    Conference findById(Integer id);

    /**
     * 会议编号和举办时间，查询会议记录（不包含参加人员数据）
     * @param map
     * @return
     */
    List<Conference> findCriteria(Map map);

    /**
     * 获取未开始的会议与参数人关系数据
     * @return
     */
    List<ConferenceItem> findOnline();

    /**
     * 根据会员id获取会员参加会议按会议记录 按时间降序
     * @param memberId
     * @return
     */
    List<Conference> getConferencesByMemberId(String memberId);

    int add(Conference conference);

    int delete(Integer id);

    int deleteBatch(String[] ids);

    int update(Conference conference);


}
