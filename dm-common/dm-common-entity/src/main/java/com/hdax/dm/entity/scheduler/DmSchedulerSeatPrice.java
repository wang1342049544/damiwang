package com.hdax.dm.entity.scheduler;


import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;



@TableName("dm_scheduler_seat_price")

public class DmSchedulerSeatPrice extends BaseEntity {

  private Long id;
  private Double price;
  private Long areaLevel;
  private Long scheduleId;
  private Long seatNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(Long areaLevel) {
        this.areaLevel = areaLevel;
    }

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Long getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(Long seatNum) {
        this.seatNum = seatNum;
    }
}
