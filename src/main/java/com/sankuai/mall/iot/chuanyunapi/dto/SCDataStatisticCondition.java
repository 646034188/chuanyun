package com.sankuai.mall.iot.chuanyunapi.dto;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

public class SCDataStatisticCondition {
  private Integer poiId;
  @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
  private Date beginTime;
  @DateTimeFormat(pattern ="yyyy-MM-dd HH:mm:ss")
  private Date endTime;

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Integer getPoiId() {
    return poiId;
  }

  public void setPoiId(Integer poiId) {
    this.poiId = poiId;
  }
}
