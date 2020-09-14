package com.micwsx.project.advertise.domain;

import java.util.Date;

/**
 * 表查询结果数据模型
 */
public class MemberHierarchy {

    private String id;
    private String refId;
    private String name;
    private String nickName;
    private String headImgUrl;
    private String sharedQRcodeUrl;
    private Date subscribeTime;
    private int level;
    private String parentName;
    private String parentNickName;
    private String parentHeadImgUrl;

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

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public String getSharedQRcodeUrl() {
        return sharedQRcodeUrl;
    }

    public void setSharedQRcodeUrl(String sharedQRcodeUrl) {
        this.sharedQRcodeUrl = sharedQRcodeUrl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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

    public String getParentHeadImgUrl() {
        return parentHeadImgUrl;
    }

    public void setParentHeadImgUrl(String parentHeadImgUrl) {
        this.parentHeadImgUrl = parentHeadImgUrl;
    }


    @Override
    public String toString() {
        return "MemberHierarchy{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", sharedQRcodeUrl='" + sharedQRcodeUrl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", level=" + level +
                ", parentName='" + parentName + '\'' +
                ", parentNickName='" + parentNickName + '\'' +
                ", parentHeadImgUrl='" + parentHeadImgUrl + '\'' +
                '}';
    }
}
