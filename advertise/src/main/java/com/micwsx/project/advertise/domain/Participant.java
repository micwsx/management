package com.micwsx.project.advertise.domain;

import java.util.Date;

public class Participant {

    private String id;
    private Integer conferenceId;
    private String memberId;
    private float paid;
    private String ticketCodeNum;
    private boolean completed;
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


    public float getPaid() {
        return paid;
    }

    public void setPaid(float paid) {
        this.paid = paid;
    }

    public String getTicketCodeNum() {
        return ticketCodeNum;
    }

    public void setTicketCodeNum(String ticketCodeNum) {
        this.ticketCodeNum = ticketCodeNum;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
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

    @Override
    public String toString() {
        return "Participant{" +
                "id='" + id + '\'' +
                ", conferenceId=" + conferenceId +
                ", memberId='" + memberId + '\'' +
                ", paid=" + paid +
                ", ticketCodeNum='" + ticketCodeNum + '\'' +
                ", completed=" + completed +
                ", ipAddr='" + ipAddr + '\'' +
                ", remark='" + remark + '\'' +
                ", changedTime=" + changedTime +
                ", createdTime=" + createdTime +
                ", data='" + data + '\'' +
                '}';
    }
}
