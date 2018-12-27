package com.sankuai.mall.iot.chuanyunapi.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SCDataCondition {
  private Integer currentPage; //查询页
  private Integer numberPerPage;//每页的数量
  private Integer poiId; //门店ID
  private Integer type;   //悬挂链区域类型
  @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
  private Date beginTime; //开始时间
  @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
  private Date endTime;   //截止时间
  private Integer lineId;//线路ID

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
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

  public Integer getPoiId() {
    return poiId;
  }

  public void setPoiId(Integer poiId) {
    this.poiId = poiId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Integer getLineId() {
    return lineId;
  }

  public void setLineId(Integer lineId) {
    this.lineId = lineId;
  }
}
