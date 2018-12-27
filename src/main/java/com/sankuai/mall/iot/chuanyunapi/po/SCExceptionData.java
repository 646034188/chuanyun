package com.sankuai.mall.iot.chuanyunapi.po;

import java.util.Date;

public class SCExceptionData {
  //数据id
  private Integer id;
  //异常数据id
  private Integer dataId;
  //监控设备序列号
  private String sn;
  //扫描到的载具序列号
  private String hfTagSn;
  //原始数据的first_capture_date字段
  private Date firstCaptureTime;
  //数据异常类型
  private Integer exceptionType;
  //数据异常类型描述
  private String exceptionTypeName;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getDataId() {
    return dataId;
  }

  public void setDataId(Integer dataId) {
    this.dataId = dataId;
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

  public Integer getExceptionType() {
    return exceptionType;
  }

  public void setExceptionType(Integer exceptionType) {
    this.exceptionType = exceptionType;
  }

  public String getExceptionTypeName() {
    return exceptionTypeName;
  }

  public void setExceptionTypeName(String exceptionTypeName) {
    this.exceptionTypeName = exceptionTypeName;
  }

  public Date getFirstCaptureTime() {
    return firstCaptureTime;
  }

  public void setFirstCaptureTime(Date firstCaptureTime) {
    this.firstCaptureTime = firstCaptureTime;
  }

  @Override
  public String toString() {
    return "SCExceptionData{" +
        "id=" + id +
        ", dataId=" + dataId +
        ", sn='" + sn + '\'' +
        ", hfTagSn='" + hfTagSn + '\'' +
        ", firstCaptureTime=" + firstCaptureTime +
        ", exceptionType=" + exceptionType +
        ", exceptionTypeName='" + exceptionTypeName + '\'' +
        '}';
  }
}
