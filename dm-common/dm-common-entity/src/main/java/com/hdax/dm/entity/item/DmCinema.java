package com.hdax.dm.entity.item;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;


@TableName("dm_cinema")
public class DmCinema extends BaseEntity {
  @TableId(type = IdType.AUTO)
  private Long id;
  private String name;
  private String address;
  private Long areaId;
  private String areaName;
  private Long xLength;
  private Long yLength;
  private String latitude;
  private String Longitude;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Long getAreaId() {
    return areaId;
  }

  public void setAreaId(Long areaId) {
    this.areaId = areaId;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public Long getxLength() {
    return xLength;
  }

  public void setxLength(Long xLength) {
    this.xLength = xLength;
  }

  public Long getyLength() {
    return yLength;
  }

  public void setyLength(Long yLength) {
    this.yLength = yLength;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return Longitude;
  }

  public void setLongitude(String longitude) {
    Longitude = longitude;
  }
}
