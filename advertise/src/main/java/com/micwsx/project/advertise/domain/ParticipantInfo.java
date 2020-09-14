package com.micwsx.project.advertise.domain;

import java.util.Date;

/**
 * 会议参数人员明细
 */
public class ParticipantInfo {

    private String id;
    private Integer conferenceId;
    private String banner;
    private String topic;
    private String brief;
    private String memberId;
    private String memberHeadImgurl;
    private String memberName;
    private Float paid;
    private String ticketCodeNum;
    private Boolean completed;
    private String ipAddr;
    private String remark;
    private Date changedTime;
    private Date createdTime;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberHeadImgurl() {
        return memberHeadImgurl;
    }

    public void setMemberHeadImgurl(String memberHeadImgurl) {
        this.memberHeadImgurl = memberHeadImgurl;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Float getPaid() {
        return paid;
    }

    public void setPaid(Float paid) {
        this.paid = paid;
    }

    public String getTicketCodeNum() {
        return ticketCodeNum;
    }

    public void setTicketCodeNum(String ticketCodeNum) {
        this.ticketCodeNum = ticketCodeNum;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getChangedTime() {
        return changedTime;
    }

    public void setChangedTime(Date changedTime) {
        this.changedTime = changedTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
