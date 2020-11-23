package com.hdax.dm.es;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Document(indexName = "dm")
public class Es {
    @Id
    private Long id;
    @Field(
            type = FieldType.Text,
            analyzer = "ik_max_word"
    )
    private String itemname;
    @Field(
            type = FieldType.Text,
            analyzer = "ik_max_word"
    )
    private String abstractmessage;
    @Field(
            type = FieldType.Integer
    )
    private Long cinemaid;
    @Field(
            type = FieldType.Double
    )
    private Double minprice;
    @Field(
            type = FieldType.Double
    )
    private Double maxprice;
    @Field(
            type = FieldType.Keyword
    )
    private String itemtype1name;
    @Field(
            type = FieldType.Keyword
    )
    private String areaname;
    @Field(
            type = FieldType.Keyword
    )
    private String adress;
    @Field(
            type = FieldType.Keyword
    )
    private String itemtype2name;
    @Field(
            type = FieldType.Date,
            format = DateFormat.date_optional_time
    )
    private LocalDate starttime;
    @Field(
            type = FieldType.Date,
            format = DateFormat.date_optional_time
    )
    private LocalDate endtime;
    @Field(
            type = FieldType.Text
    )
    private String imgurl;
    @Field(
            type = FieldType.Text
    )
    private String latitude;
    @Field(
            type = FieldType.Text
    )
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public LocalDate getStarttime() {
        return starttime;
    }

    public void setStarttime(LocalDate starttime) {
        this.starttime = starttime;
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

    public Long getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Long cinemaid) {
        this.cinemaid = cinemaid;
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

    public String getItemtype1name() {
        return itemtype1name;
    }

    public void setItemtype1name(String itemtype1name) {
        this.itemtype1name = itemtype1name;
    }

    public String getItemtype2name() {
        return itemtype2name;
    }

    public void setItemtype2name(String itemtype2name) {
        this.itemtype2name = itemtype2name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAbstractmessage() {
        return abstractmessage;
    }

    public void setAbstractmessage(String abstractmessage) {
        this.abstractmessage = abstractmessage;
    }

    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    public LocalDate getEndtime() {
        return endtime;
    }

    public void setEndtime(LocalDate endtime) {
        this.endtime = endtime;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
