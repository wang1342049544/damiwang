package com.hdax.dm.vo.juchang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleIdBySeatResponse implements Serializable {

  // 根据排期获取剧场座位信息
  private Long scheduleId;
  private Long cinemaId;
  //座位价格列表
  private List<SeatPriceListResponse> seatPriceList=new ArrayList<>();
  //座位信息列表(座位状态信息)
  private List<SeatInfoListResponse> seatInfoList=new ArrayList<>();


  public Long getScheduleId() {
    return scheduleId;
  }

  public void setScheduleId(Long scheduleId) {
    this.scheduleId = scheduleId;
  }

  public Long getCinemaId() {
    return cinemaId;
  }

  public void setCinemaId(Long cinemaId) {
    this.cinemaId = cinemaId;
  }

  public List<SeatPriceListResponse> getSeatPriceList() {
    return seatPriceList;
  }

  public void setSeatPriceList(List<SeatPriceListResponse> seatPriceList) {
    this.seatPriceList = seatPriceList;
  }

  public List<SeatInfoListResponse> getSeatInfoList() {
    return seatInfoList;
  }

  public void setSeatInfoList(List<SeatInfoListResponse> seatInfoList) {
    this.seatInfoList = seatInfoList;
  }
}
