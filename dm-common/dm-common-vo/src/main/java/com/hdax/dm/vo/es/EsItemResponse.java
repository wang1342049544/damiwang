package com.hdax.dm.vo.es;

import java.io.Serializable;

public class EsItemResponse implements Serializable {
    private Integer areaId;
    private Integer currentPage;
    private String endTime;
    private Integer itemTypeId1;
    private Integer itemTypeId2;
    private String keyword;
    private Integer pageSize;
    private String sort;
    private String startTime;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getItemTypeId1() {
        return itemTypeId1;
    }

    public void setItemTypeId1(Integer itemTypeId1) {
        this.itemTypeId1 = itemTypeId1;
    }

    public Integer getItemTypeId2() {
        return itemTypeId2;
    }

    public void setItemTypeId2(Integer itemTypeId2) {
        this.itemTypeId2 = itemTypeId2;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}
