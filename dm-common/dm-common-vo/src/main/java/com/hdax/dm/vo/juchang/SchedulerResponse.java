package com.hdax.dm.vo.juchang;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class SchedulerResponse implements Serializable {

    //根据剧场id得到剧场的原始座位
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
