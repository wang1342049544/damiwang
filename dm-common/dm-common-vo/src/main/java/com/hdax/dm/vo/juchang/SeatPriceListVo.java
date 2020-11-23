package com.hdax.dm.vo.juchang;


import java.io.Serializable;

public class SeatPriceListVo implements Serializable {

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
