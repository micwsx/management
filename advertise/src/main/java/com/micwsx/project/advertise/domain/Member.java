package com.micwsx.project.advertise.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Member {

    private String id;
    private String refId;
    private String name;
    private String mobile;
    private String email;
    private String sharedQRcodeUrl;
    private boolean active=true;
    private boolean subscribe;
    private String nickName;
    private boolean gender=true;
    private String lang;
    private String city;
    private String province;
    private String country;
    private String headImgurl;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date subscribeTime;
    private String unionId;
    private String groupId;
    private String tagidlist;
    private String subscribeScene;
    private String qrScene;
    private String qrSceneStr;
    private String latitude;
    private String longitude;
    private String precision;
    private boolean onBlackList;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date changedTime;
    private String remark;

    public Member() {
    }

    public Member(String id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSharedQRcodeUrl() {
        return sharedQRcodeUrl;
    }

    public void setSharedQRcodeUrl(String sharedQRcodeUrl) {
        this.sharedQRcodeUrl = sharedQRcodeUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadImgurl() {
        return headImgurl;
    }

    public void setHeadImgurl(String headImgurl) {
        this.headImgurl = headImgurl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTagidlist() {
        return tagidlist;
    }

    public void setTagidlist(String tagidlist) {
        this.tagidlist = tagidlist;
    }

    public String getSubscribeScene() {
        return subscribeScene;
    }

    public void setSubscribeScene(String subscribeScene) {
        this.subscribeScene = subscribeScene;
    }

    public String getQrScene() {
        return qrScene;
    }

    public void setQrScene(String qrScene) {
        this.qrScene = qrScene;
    }

    public String getQrSceneStr() {
        return qrSceneStr;
    }

    public void setQrSceneStr(String qrSceneStr) {
        this.qrSceneStr = qrSceneStr;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getPrecision() {
        return precision;
    }

    public void setPrecision(String precision) {
        this.precision = precision;
    }

    public boolean isOnBlackList() {
        return onBlackList;
    }

    public void setOnBlackList(boolean onBlackList) {
        this.onBlackList = onBlackList;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Date changedTime) {
        this.changedTime = changedTime;
    }


    @Override
    public String toString() {
        return "Member{" +
                "id='" + id + '\'' +
                ", refId='" + refId + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", sharedQRcodeUrl='" + sharedQRcodeUrl + '\'' +
                ", active=" + active +
                ", subscribe=" + subscribe +
                ", nickName='" + nickName + '\'' +
                ", gender=" + gender +
                ", lang='" + lang + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", headImgurl='" + headImgurl + '\'' +
                ", subscribeTime=" + subscribeTime +
                ", unionId='" + unionId + '\'' +
                ", groupId='" + groupId + '\'' +
                ", tagidlist='" + tagidlist + '\'' +
                ", subscribeScene='" + subscribeScene + '\'' +
                ", qrScene='" + qrScene + '\'' +
                ", qrSceneStr='" + qrSceneStr + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", precision='" + precision + '\'' +
                ", onBlackList=" + onBlackList +
                ", createdTime=" + createdTime +
                ", changedTime=" + changedTime +
                ", remark='" + remark + '\'' +
                '}';
    }
}
