package com.micwsx.project.advertise.dao;

import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.domain.MemberHierarchy;

import java.util.List;
import java.util.Map;

public interface MemberMapper {

    Member selectById(String id);

    List<Member> selectAll();

    // 支持id,name,mobile过滤
    List<Member> selectCriteria(Member member);

    int add(Member member);

    int delete(String id);

    int deleteBatch(String[] ids);

    // 只允许修改refId,name,mobile,email,active,remark
    int update(Member member);

    // 统一修改某些字段，是可以实现的。subscribe,idList
    int batchUpdateSubscribe(Map map);


    // 获取所有子成员结构数据
    List<MemberHierarchy> getChildTable(String id);

    void getParentList(Map<String, String> map);




}
