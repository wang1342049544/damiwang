package com.hdax.dm.vo.juchang;

import java.io.Serializable;

public class SeatInfoListResponse implements Serializable {

  //座位信息的视图
  private Long id;
  private Long x;
  private Long y;
  private String areaLevel;
  private Long cinemaId;
  private Long sort;
  private Long status;

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

  public String getAreaLevel() {
    return areaLevel;
  }

  public void setAreaLevel(String areaLevel) {
    this.areaLevel = areaLevel;
  }

  public Long getCinemaId() {
    return cinemaId;
  }

  public void setCinemaId(Long cinemaId) {
    this.cinemaId = cinemaId;
  }

  public Long getSort() {
    return sort;
  }

  public void setSort(Long sort) {
    this.sort = sort;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }
}
