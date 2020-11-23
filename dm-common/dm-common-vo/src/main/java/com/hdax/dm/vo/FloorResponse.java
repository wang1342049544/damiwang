package com.hdax.dm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FloorResponse implements Serializable {

  private Integer floor;
  private Integer index;
  private String itemTypeName;
  private Long itemTypeId;
  private List<ItemsResponse> items=new ArrayList();

  public Integer getIndex() {
    return index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public Integer getFloor() {
    return floor;
  }

  public void setFloor(Integer floor) {
    this.floor = floor;
  }

  public Long getItemTypeId() {
    return itemTypeId;
  }

  public void setItemTypeId(Long itemTypeId) {
    this.itemTypeId = itemTypeId;
  }

  public String getItemTypeName() {
    return itemTypeName;
  }

  public void setItemTypeName(String itemTypeName) {
    this.itemTypeName = itemTypeName;
  }



  public List<ItemsResponse> getItems() {
    return items;
  }

  public void setItems(List<ItemsResponse> items) {
    this.items = items;
  }

}
