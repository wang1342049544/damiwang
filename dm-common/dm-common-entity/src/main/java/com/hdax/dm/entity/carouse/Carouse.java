package com.hdax.dm.entity.carouse;

import java.io.Serializable;

/**
 * 轮播图
 */
public class Carouse implements Serializable {
    private  Long id;
    private  String itemName;
    private  Double minPrice;
    private  String imgUrl;




    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
