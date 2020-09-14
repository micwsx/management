package com.micwsx.project.advertise.domain;

import java.util.Date;

/**
 * @author Michael
 * @create 7/28/2020 4:00 PM
 * 查询会议，会议参加总人数，总支付金额
 */
public class ParticipantItem {

    private Integer conferenceId;
    private String banner;//会议banner
    private String topic;//会议主题
    private String brief;//会议主题
    private Date startTime;//会议时间
    private String hoster;//主持人
    private Integer seatNum; // 座位数
    private Float fee; // 入场费用
    private Integer attendentNum; //总参数人数
    private Double total;//总支付金额
    private boolean released;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Integer seatNum) {
        this.seatNum = seatNum;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getHoster() {
        return hoster;
    }

    public void setHoster(String hoster) {
        this.hoster = hoster;
    }

    public Integer getAttendentNum() {
        return attendentNum;
    }

    public void setAttendentNum(Integer attendentNum) {
        this.attendentNum = attendentNum;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
