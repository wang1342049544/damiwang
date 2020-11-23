package com.hdax.dm.vo.order;

import java.io.Serializable;

public class OrderResponse implements Serializable {
  private Long id;
  private String orderNo;
  private String itemName;
  private Double totalAmount;
  private Long orderType;
  private Long num;
  private Double unitPrice;
  private String sellTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public Double getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    this.totalAmount = totalAmount;
  }

  public Long getOrderType() {
    return orderType;
  }

  public void setOrderType(Long orderType) {
    this.orderType = orderType;
  }

  public Long getNum() {
    return num;
  }

  public void setNum(Long num) {
    this.num = num;
  }

  public Double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getSellTime() {
    return sellTime;
  }

  public void setSellTime(String sellTime) {
    this.sellTime = sellTime;
  }
}
