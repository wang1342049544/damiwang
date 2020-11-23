package com.hdax.dm.vo.juchang;

import java.io.Serializable;

public class SeatPriceListResponse implements Serializable {


  //座位价格视图
  private String areaLevelName;
  private Double price;

  public String getAreaLevelName() {
    return areaLevelName;
  }

  public void setAreaLevelName(String areaLevelName) {
    this.areaLevelName = areaLevelName;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
