package com.hdax.dm.vo.juchang;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ScheduleIdSeat implements Serializable {

    private Long cinemaId;
    private List<String> seatArray=new ArrayList<>();

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }

    public List<String> getSeatArray() {
        return seatArray;
    }

    public void setSeatArray(List<String> seatArray) {
        this.seatArray = seatArray;
    }
}
