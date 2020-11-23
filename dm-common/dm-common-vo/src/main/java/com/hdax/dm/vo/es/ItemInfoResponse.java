package com.hdax.dm.vo.es;

import java.io.Serializable;
import java.util.List;

public class ItemInfoResponse implements Serializable {

    private Integer currentPage;
    private Integer pageCount;
    private Integer pageSize;
    private List<ItemRowsResponse> rows;
    private Integer total;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<ItemRowsResponse> getRows() {
        return rows;
    }

    public void setRows(List<ItemRowsResponse> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
