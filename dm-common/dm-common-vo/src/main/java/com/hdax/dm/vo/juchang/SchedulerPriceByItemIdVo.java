package com.hdax.dm.vo.juchang;

import java.io.Serializable;

public class SchedulerPriceByItemIdVo implements Serializable {

    private Long id;
    private Long scheduleId;
    private Double price;
    private Long isHaveSeat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getIsHaveSeat() {
        return isHaveSeat;
    }

    public void setIsHaveSeat(Long isHaveSeat) {
        this.isHaveSeat = isHaveSeat;
    }
}
