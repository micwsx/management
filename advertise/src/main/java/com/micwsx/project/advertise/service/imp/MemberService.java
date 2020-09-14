package com.micwsx.project.advertise.service.imp;

import com.micwsx.project.advertise.dao.MemberMapper;
import com.micwsx.project.advertise.domain.Member;
import com.micwsx.project.advertise.domain.MemberHierarchy;
import com.micwsx.project.advertise.service.TMemberService;
import com.micwsx.project.advertise.utility.DateUtil;
import com.micwsx.project.advertise.viewmodel.PersonalCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberService implements TMemberService {

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public Member get(String id) {
        return memberMapper.selectById(id);
    }

    @Override
    public List<Member> findAll() {
        return memberMapper.selectAll();
    }

    @Override
    public List<Member> find(Member member) {
        return memberMapper.selectCriteria(member);
    }

    @Override
    public int add(Member member) {
        return memberMapper.add(member);
    }

    @Override
    public int delete(String id) {
        return memberMapper.delete(id);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return memberMapper.deleteBatch(ids);
    }

    @Override
    public int update(Member member) {
        return memberMapper.update(member);
    }

    @Override
    public PersonalCenter getPersonalDate(String id) {
        List<MemberHierarchy> list = memberMapper.getChildTable(id);
        PersonalCenter personalCenter = new PersonalCenter();
        MemberHierarchy owner = list.get(0);
        personalCenter.setId(owner.getId());
        personalCenter.setHeadImgurl(owner.getHeadImgUrl());
        personalCenter.setName(owner.getName());
        personalCenter.setNickName(owner.getNickName());
        personalCenter.setSubscribeTime(owner.getSubscribeTime());
        personalCenter.setRefId(owner.getRefId());
        personalCenter.setParentName(owner.getParentName());
        personalCenter.setParentNickName(owner.getParentNickName());
        personalCenter.setParentHeadImgurl(owner.getParentHeadImgUrl());
        personalCenter.setRecommendQRCodeSrc(owner.getSharedQRcodeUrl());
        personalCenter.setTotal(list.size() - 1);

        // 获取关系成员
        list.stream().filter(m->m.getLevel()>0).forEach(m -> {
            if (DateUtil.isToday(m.getSubscribeTime())) {
                int todayNum = personalCenter.getTodayNum() + 1;
                personalCenter.setTodayNum(todayNum);
            }
            if (DateUtil.isYesterday(m.getSubscribeTime())) {
                int yesterdayNum = personalCenter.getYesterdayNum() + 1;
                personalCenter.setYesterdayNum(yesterdayNum);
            }
            if (m.getLevel() == 1)
                personalCenter.getDirectMembers().add(m);
            else if (m.getLevel() > 1)
                personalCenter.getInDirectMembers().add(m);
        });
        return personalCenter;
    }

    @Override
    public String getParentList(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
        memberMapper.getParentList(map);
        return map.get("parentList");
    }

    @Override
    public int batchUpdateSubscribe(Map map) {
        return memberMapper.batchUpdateSubscribe(map);
    }
}
