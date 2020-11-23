package com.hdax.dm.entity.goods;



import java.util.ArrayList;
import java.util.List;

public class DmEsItemReturn {

  private List<DmEsItem> list=new ArrayList<>();
  private Long totalPages;
  private Long totalElements;

  public List<DmEsItem> getList() {
    return list;
  }

  public void setList(List<DmEsItem> list) {
    this.list = list;
  }

  public Long getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Long totalPages) {
    this.totalPages = totalPages;
  }

  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }
}
