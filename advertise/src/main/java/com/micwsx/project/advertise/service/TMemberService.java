package com.micwsx.project.advertise.service;

import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.viewmodel.PersonalCenter;

import java.util.List;
import java.util.Map;

public interface TMemberService {



    Member get(String id);

    List<Member> findAll();

    // 支持id,name,mobile过滤
    List<Member> find(Member member);

    int add(Member member);

    int delete(String id);

    int deleteBatch(String[] ids);

    // 只允许修改refId,name,mobile,email,active,remark
    int update(Member member);

    // 获取所有子成员结构数据
    PersonalCenter getPersonalDate(String id);

    // 获取所有父成员id集合
    String getParentList(String id);

    int batchUpdateSubscribe(Map map);


}
