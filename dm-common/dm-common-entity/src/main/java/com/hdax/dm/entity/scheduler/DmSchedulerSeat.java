package com.hdax.dm.entity.scheduler;


import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;



@TableName("dm_scheduler_seat")

public class DmSchedulerSeat extends BaseEntity {

  private Long id;
  //x坐标
  private Long x;
  //y坐标
  private Long y;
  //区域级别(1:A;2:B;3:C)
  private Long areaLevel;
  //排期Id
  private Long scheduleId;
  //订单编号
  private String orderNo;
  //如果已经售出记录购买用户id
  private Long userId;
  //座位状态(0:没座位 1:有座位 2:锁定待付款 3:已售出 )
  private Long status;
  //排序
  private Long sort;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    public Long getY() {
        return y;
    }

    public void setY(Long y) {
        this.y = y;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }
}
