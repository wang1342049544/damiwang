package com.hdax.dm.vo.es;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AreaItemVo implements Serializable {

    private Long currentPage;
    private Long pageCount;
    private Long pageSize;
    private List<AreaItemRowsVo> rows=new ArrayList();
    private Long total;

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getPageCount() {
        return pageCount;
    }

    public void setPageCount(Long pageCount) {
        this.pageCount = pageCount;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public List<AreaItemRowsVo> getRows() {
        return rows;
    }

    public void setRows(List<AreaItemRowsVo> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
