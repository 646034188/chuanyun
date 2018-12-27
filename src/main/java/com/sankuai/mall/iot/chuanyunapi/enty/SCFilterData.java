package com.sankuai.mall.iot.chuanyunapi.enty;

import java.util.Date;

public class SCFilterData {
  private Integer id;
  private String sn; //监控设备sn
  private String hfTagSn;//扫描到的载具sn
  private Date firstCaptureTime;//载具开始扫描到的时间
  private Date lastCaptureTime;//载具最后被扫描到的时间

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public String getHfTagSn() {
    return hfTagSn;
  }

  public void setHfTagSn(String hfTagSn) {
    this.hfTagSn = hfTagSn;
  }

  public Date getFirstCaptureTime() {
    return firstCaptureTime;
  }

  public void setFirstCaptureTime(Date firstCaptureTime) {
    this.firstCaptureTime = firstCaptureTime;
  }

  public Date getLastCaptureTime() {
    return lastCaptureTime;
  }

  public void setLastCaptureTime(Date lastCaptureTime) {
    this.lastCaptureTime = lastCaptureTime;
  }
}
