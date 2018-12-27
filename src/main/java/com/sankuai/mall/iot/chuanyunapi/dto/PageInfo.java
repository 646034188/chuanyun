package com.sankuai.mall.iot.chuanyunapi.dto;

import java.util.List;

public class PageInfo {
  private Integer currentPage;
  private Integer numberPerPage;
  private Integer totalPage;
  private List list;

  public PageInfo(Integer currentPage, Integer numberPerPage, Integer  totalPage, List list) {
    this.currentPage = currentPage;
    this.numberPerPage = numberPerPage;
    this.totalPage = totalPage;
    this.list = list;
  }

  public PageInfo(){
  }

  public Integer getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Integer currentPage) {
    this.currentPage = currentPage;
  }

  public Integer getNumberPerPage() {
    return numberPerPage;
  }

  public void setNumberPerPage(Integer numberPerPage) {
    this.numberPerPage = numberPerPage;
  }

  public Integer  getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(Integer  totalPage) {
    this.totalPage = totalPage;
  }

  public List getList() {
    return list;
  }

  public void setList(List list) {
    this.list = list;
  }
}
