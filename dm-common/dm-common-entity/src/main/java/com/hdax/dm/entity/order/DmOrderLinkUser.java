package com.hdax.dm.entity.order;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;


@TableName("dm_order_link_user")
public class DmOrderLinkUser extends BaseEntity {
  @TableId(type = IdType.AUTO)
  private Long id;
  private Long orderId;
  private Long linkUserId;
  private String linkUserName;
  private Long X;
  private Long Y;
  private Double price;
  


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }


  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }


  public Long getLinkUserId() {
    return linkUserId;
  }

  public void setLinkUserId(Long linkUserId) {
    this.linkUserId = linkUserId;
  }


  public String getLinkUserName() {
    return linkUserName;
  }

  public void setLinkUserName(String linkUserName) {
    this.linkUserName = linkUserName;
  }


  public Long getX() {
    return X;
  }

  public void setX(Long X) {
    this.X = X;
  }


  public Long getY() {
    return Y;
  }

  public void setY(Long Y) {
    this.Y = Y;
  }


  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
  

}
