package com.micwsx.project.advertise.domain;

import java.util.Date;
import java.util.List;

public class Conference {
    private Integer id;
    private String serialNumber;
    private String banner;
    private String topic;
    private String brief;
    private String introduction;
    private String location;
    private String siteLink;
    private String siteBrief;
    private String hoster;
    private String hosterLink;
    private String hosterBrief;
    private String guest;
    private String guestLink;
    private String guestBrief;
    private float fee;
    private Date startTime;
    private Date endTime;
    private boolean released=true;
    private int seatNum;
    private String remark;
    private Date createdTime;

    private List<ParticipantInfo> participants;

    public List<ParticipantInfo> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantInfo> participants) {
        this.participants = participants;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSiteLink() {
        return siteLink;
    }

    public void setSiteLink(String siteLink) {
        this.siteLink = siteLink;
    }

    public String getSiteBrief() {
        return siteBrief;
    }

    public void setSiteBrief(String siteBrief) {
        this.siteBrief = siteBrief;
    }

    public String getHoster() {
        return hoster;
    }

    public void setHoster(String hoster) {
        this.hoster = hoster;
    }

    public String getHosterLink() {
        return hosterLink;
    }

    public void setHosterLink(String hosterLink) {
        this.hosterLink = hosterLink;
    }

    public String getHosterBrief() {
        return hosterBrief;
    }

    public void setHosterBrief(String hosterBrief) {
        this.hosterBrief = hosterBrief;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public String getGuestLink() {
        return guestLink;
    }

    public void setGuestLink(String guestLink) {
        this.guestLink = guestLink;
    }

    public String getGuestBrief() {
        return guestBrief;
    }

    public void setGuestBrief(String guestBrief) {
        this.guestBrief = guestBrief;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
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

    @Override
    public String toString() {
        return "Conference{" +
                "id=" + id +
                ", serialNumber='" + serialNumber + '\'' +
                ", topic='" + topic + '\'' +
                ", brief='" + brief + '\'' +
                ", introduction='" + introduction + '\'' +
                ", location='" + location + '\'' +
                ", siteLink='" + siteLink + '\'' +
                ", siteBrief='" + siteBrief + '\'' +
                ", hoster='" + hoster + '\'' +
                ", hosterLink='" + hosterLink + '\'' +
                ", hosterBrief='" + hosterBrief + '\'' +
                ", guest='" + guest + '\'' +
                ", guestLink='" + guestLink + '\'' +
                ", guestBrief='" + guestBrief + '\'' +
                ", fee=" + fee +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", released=" + released +
                ", seatNum=" + seatNum +
                ", remark='" + remark + '\'' +
                ", createdTime=" + createdTime +
                ", participants=" + participants +
                '}';
    }
}
