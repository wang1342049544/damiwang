package com.hdax.dm.entity.goods;


import com.hdax.dm.BaseEntity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "dm")

public class DmEsItem extends BaseEntity {
  private Long state;
  @Id
  private Long id;
  @Field(type = FieldType.Text)
  private String itemname;
  @Field(type = FieldType.Text)
  private String abstractmessage;
  private Long itemtype1id;
  @Field(type = FieldType.Text)
  private String itemtype1name;
  private Long itemtype2id;
  private String itemtype2name;
  private Long isbanner;
  private String longitude;
  private String latitude;
  @Field(type = FieldType.Date,format = DateFormat.date_optional_time)
  private String starttime;
  @Field(type = FieldType.Date,format = DateFormat.date_optional_time)
  private String endtime;
  private Double minprice;
  private Double maxprice;
  private String imgurl;
  private String areaName;
  private String adress;
  private Long cinemaid;


  public Long getState() {
    return state;
  }

  public void setState(Long state) {
    this.state = state;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getItemname() {
    return itemname;
  }

  public void setItemname(String itemname) {
    this.itemname = itemname;
  }

  public String getAbstractmessage() {
    return abstractmessage;
  }

  public void setAbstractmessage(String abstractmessage) {
    this.abstractmessage = abstractmessage;
  }

  public Long getItemtype1id() {
    return itemtype1id;
  }

  public void setItemtype1id(Long itemtype1id) {
    this.itemtype1id = itemtype1id;
  }

  public String getItemtype1name() {
    return itemtype1name;
  }

  public void setItemtype1name(String itemtype1name) {
    this.itemtype1name = itemtype1name;
  }

  public Long getItemtype2id() {
    return itemtype2id;
  }

  public void setItemtype2id(Long itemtype2id) {
    this.itemtype2id = itemtype2id;
  }

  public String getItemtype2name() {
    return itemtype2name;
  }

  public void setItemtype2name(String itemtype2name) {
    this.itemtype2name = itemtype2name;
  }

  public Long getIsbanner() {
    return isbanner;
  }

  public void setIsbanner(Long isbanner) {
    this.isbanner = isbanner;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getStarttime() {
    return starttime;
  }

  public void setStarttime(String starttime) {
    this.starttime = starttime;
  }

  public String getEndtime() {
    return endtime;
  }

  public void setEndtime(String endtime) {
    this.endtime = endtime;
  }

  public Double getMinprice() {
    return minprice;
  }

  public void setMinprice(Double minprice) {
    this.minprice = minprice;
  }

  public Double getMaxprice() {
    return maxprice;
  }

  public void setMaxprice(Double maxprice) {
    this.maxprice = maxprice;
  }

  public String getImgurl() {
    return imgurl;
  }

  public void setImgurl(String imgurl) {
    this.imgurl = imgurl;
  }

  public String getAreaName() {
    return areaName;
  }

  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  public String getAdress() {
    return adress;
  }

  public void setAdress(String adress) {
    this.adress = adress;
  }

  public Long getCinemaid() {
    return cinemaid;
  }

  public void setCinemaid(Long cinemaid) {
    this.cinemaid = cinemaid;
  }


}
