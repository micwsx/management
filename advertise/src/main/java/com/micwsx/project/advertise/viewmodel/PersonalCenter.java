package com.micwsx.project.advertise.viewmodel;

import com.micwsx.project.advertise.domain.MemberHierarchy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersonalCenter {
    private String id; // 就是openid
    private String headImgurl;
    private String name;
    private String nickName;
    private Date subscribeTime;
    private String refId;// 就是openid
    private String parentName;
    private String parentNickName;
    private String parentHeadImgurl;
    private String recommendQRCodeSrc;

    private int total;
    private int todayNum;
    private int yesterdayNum;
    private List<MemberHierarchy> directMembers = new ArrayList<>();
    private List<MemberHierarchy> inDirectMembers = new ArrayList<>();
    private List recentActivities = new ArrayList();//最近参与会议记录

    public String getRecommendQRCodeSrc() {
        return recommendQRCodeSrc;
    }

    public void setRecommendQRCodeSrc(String recommendQRCodeSrc) {
        this.recommendQRCodeSrc = recommendQRCodeSrc;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentNickName() {
        return parentNickName;
    }

    public void setParentNickName(String parentNickName) {
        this.parentNickName = parentNickName;
    }

    public String getParentHeadImgurl() {
        return parentHeadImgurl;
    }

    public void setParentHeadImgurl(String parentHeadImgurl) {
        this.parentHeadImgurl = parentHeadImgurl;
    }

    public int getTodayNum() {
        return todayNum;
    }

    public void setTodayNum(int todayNum) {
        this.todayNum = todayNum;
    }

    public int getYesterdayNum() {
        return yesterdayNum;
    }

    public void setYesterdayNum(int yesterdayNum) {
        this.yesterdayNum = yesterdayNum;
    }

    public List<MemberHierarchy> getDirectMembers() {
        return directMembers;
    }

    public List<MemberHierarchy> getInDirectMembers() {
        return inDirectMembers;
    }

    public List getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(List recentActivities) {
        this.recentActivities = recentActivities;
    }

    @Override
    public String toString() {
        return "PersonalCenter{" +
                "id='" + id + '\'' +
                ", headImgurl='" + headImgurl + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", refId='" + refId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentNickName='" + parentNickName + '\'' +
                ", parentHeadImgurl='" + parentHeadImgurl + '\'' +
                ", total=" + total +
                ", todayNum=" + todayNum +
                ", yesterdayNum=" + yesterdayNum +
                ", directMembers=" + directMembers.size() +
                ", inDirectMembers=" + inDirectMembers.size() +
                ", recentActivities=" + recentActivities.size() +
                '}';
    }
}
