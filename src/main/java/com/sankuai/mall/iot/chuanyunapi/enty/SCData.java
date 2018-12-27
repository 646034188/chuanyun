package com.sankuai.mall.iot.chuanyunapi.enty;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class SCData {
  private Long id;
  private String sn;
  private String hfTagSn;
  private Integer poiId;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm::ss",timezone = "GMT+8")
  private Date captureTime;
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHfTagSn() {
    return hfTagSn;
  }

  public void setHfTagSn(String hfTagSn) {
    this.hfTagSn = hfTagSn;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public Integer getPoiId() {
    return poiId;
  }

  public void setPoiId(Integer poiId) {
    this.poiId = poiId;
  }

  public Date getCaptureTime() {
    return captureTime;
  }

  public void setCaptureTime(Date captureTime) {
    this.captureTime = captureTime;
  }
}
