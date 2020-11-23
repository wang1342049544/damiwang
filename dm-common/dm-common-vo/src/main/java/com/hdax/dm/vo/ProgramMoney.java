package com.hdax.dm.vo;

import java.io.Serializable;

public class ProgramMoney implements Serializable {

    private String itemName;
    private String  seatNames;
    private Integer seatCount;
    private Double  totalAMount;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSeatNames() {
        return seatNames;
    }

    public void setSeatNames(String seatNames) {
        this.seatNames = seatNames;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Double getTotalAMount() {
        return totalAMount;
    }

    public void setTotalAMount(Double totalAMount) {
        this.totalAMount = totalAMount;
    }
}
