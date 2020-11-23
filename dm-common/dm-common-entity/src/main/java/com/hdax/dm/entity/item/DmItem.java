package com.hdax.dm.entity.item;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hdax.dm.BaseEntity;


/**
 *  首页表
 */
import java.time.LocalDateTime;

@TableName("dm_item") //表名字
public class DmItem  extends BaseEntity {

    @TableId(type =  IdType.AUTO)
  private Long id;
  private String itemName;
  private String abstractMessage;
  private Long itemType1Id;
  private String itemType1Name;
  private Long itemType2Id;
  private String itemType2Name;
  private Long state;
  private String basicDescription;
  private String projectDescription;
  private String reminderDescription;
  private String longitude;
  private String latitude;
  private Long isBanner;
  private Long isRecommend;
  private Double avgScore;
  private Long commentCount;
  private Long cinemaId;
  private String startTime;
  private String endTime;
  private Double minPrice;
  private Double maxPrice;
  private Long ageGroup;
  private LocalDateTime createdTime;
  private LocalDateTime updatedTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getAbstractMessage() {
        return abstractMessage;
    }

    public void setAbstractMessage(String abstractMessage) {
        this.abstractMessage = abstractMessage;
    }

    public Long getItemType1Id() {
        return itemType1Id;
    }

    public void setItemType1Id(Long itemType1Id) {
        this.itemType1Id = itemType1Id;
    }

    public String getItemType1Name() {
        return itemType1Name;
    }

    public void setItemType1Name(String itemType1Name) {
        this.itemType1Name = itemType1Name;
    }

    public Long getItemType2Id() {
        return itemType2Id;
    }

    public void setItemType2Id(Long itemType2Id) {
        this.itemType2Id = itemType2Id;
    }

    public String getItemType2Name() {
        return itemType2Name;
    }

    public void setItemType2Name(String itemType2Name) {
        this.itemType2Name = itemType2Name;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getBasicDescription() {
        return basicDescription;
    }

    public void setBasicDescription(String basicDescription) {
        this.basicDescription = basicDescription;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getReminderDescription() {
        return reminderDescription;
    }

    public void setReminderDescription(String reminderDescription) {
        this.reminderDescription = reminderDescription;
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

    public Long getIsBanner() {
        return isBanner;
    }

    public void setIsBanner(Long isBanner) {
        this.isBanner = isBanner;
    }

    public Long getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Long isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Double getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Double avgScore) {
        this.avgScore = avgScore;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Long getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Long cinemaId) {
        this.cinemaId = cinemaId;
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

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Long getAgeGroup() {
        return ageGroup;
    }

    public void setAgeGroup(Long ageGroup) {
        this.ageGroup = ageGroup;
    }

    @Override
    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    @Override
    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}
