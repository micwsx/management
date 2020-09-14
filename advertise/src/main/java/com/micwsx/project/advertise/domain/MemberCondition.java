package com.micwsx.project.advertise.domain;

/**
 * @author Michael
 * @create 7/30/2020 1:18 PM
 */
public class MemberCondition {
    private Integer id;//会议id
    private String memberId;
    private String memberName;
    private Boolean completed;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "MemberCondtion{" +
                "id=" + id +
                ", memberId='" + memberId + '\'' +
                ", memberName='" + memberName + '\'' +
                ", completed=" + completed +
                '}';
    }
}
