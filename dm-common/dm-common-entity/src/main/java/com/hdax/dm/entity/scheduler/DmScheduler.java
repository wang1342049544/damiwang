package com.hdax.dm.entity.scheduler;


import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;



import java.util.Date;


@TableName("dm_scheduler")

public class DmScheduler extends BaseEntity {

  private Long id;
  private String title;
  private Date startTime;
  private Date endTime;
  private Long itemId;
  private Long cinemaId;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
    }
}
