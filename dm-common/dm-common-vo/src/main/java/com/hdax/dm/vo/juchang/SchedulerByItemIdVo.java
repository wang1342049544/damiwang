package com.hdax.dm.vo.juchang;

import java.io.Serializable;
import java.util.Date;

public class SchedulerByItemIdVo implements Serializable {

    private Long id;
    private String title;
    private Long itemId;
    private String startTime;
    private String endTime;
    private Long cinemaId;

    public SchedulerByItemIdVo(Long id, String title, Long itemId, Date startTime, Date endTime, Long cinemaId) {
    }

    public SchedulerByItemIdVo(Long id, String title, Long itemId, String startTime, String endTime, Long cinemaId) {
        this.id = id;
        this.title = title;
        this.itemId = itemId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cinemaId = cinemaId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }
}
