package com.micwsx.project.advertise.dao;


import com.micwsx.project.advertise.domain.Conference;
import com.micwsx.project.advertise.domain.ConferenceItem;

import java.util.List;
import java.util.Map;

public interface ConferenceMapper {

    /**
     * 根据会议Id获取会议成员数据
     * @param id
     * @return
     */
    Conference selectById(Integer id);

    /**
     * 查询会议记录（不包含参加人员数据）,根据会议编号和举办时间
     * @param map
     * @return
     */
    List<Conference> selectAll(Map map);

    /**
     * 获取未开始的会议与参数人关系数据
     * @return
     */
    List<ConferenceItem> selectValid();

    /**
     * 根据会员id获取会员参加会议按会议记录 按时间降序
     * @param memberId：会员id
     * @return
     */
    List<Conference> selectConferences(String memberId);

    int add(Conference conference);

    int delete(Integer id);

    int deleteBatch(String[] ids);

    int update(Conference conference);

}
