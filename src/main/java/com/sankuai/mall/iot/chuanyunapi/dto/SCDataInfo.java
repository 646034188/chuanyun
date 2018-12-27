package com.sankuai.mall.iot.chuanyunapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class SCDataInfo {
  private String hfTagSn; //载具高频RFID标签
   private Date firstTime;//进入相应区域时间
  private Date endTime;//离开区域时间
  private Long useTime;
  // 数据类型
  private Integer dataType;

  private String regionName; //区域名称

  public String getHfTagSn() {
    return hfTagSn;
  }

  public void setHfTagSn(String hfTagSn) {
    this.hfTagSn = hfTagSn;
  }
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  public Date getFirstTime() {
    return firstTime;
  }

  public void setFirstTime(Date firstTime) {
    this.firstTime = firstTime;
  }
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public Integer getDataType() {
    return dataType;
  }

  public void setDataType(Integer dataType) {
    this.dataType = dataType;
  }

  public void setUseTime(Long useTime) {
    this.useTime = useTime;
  }

  public Long getUseTime() {
    return useTime;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }
}
