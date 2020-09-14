package com.micwsx.project.advertise.viewmodel;

public class Product {
    private Integer conferenceId;
    private String description;
    private float amount;
    private String ticketCode;
    private String remark;

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTicketCode() {
        return ticketCode;
    }

    public void setTicketCode(String ticketCode) {
        this.ticketCode = ticketCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "Product{" +
                "conferenceId=" + conferenceId +
                ", description='" + description + '\'' +
                ", amount='" + amount + '\'' +
                ", ticketCode='" + ticketCode + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
