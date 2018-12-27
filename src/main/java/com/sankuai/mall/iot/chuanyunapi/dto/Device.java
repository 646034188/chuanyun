package com.sankuai.mall.iot.chuanyunapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;

public class Device {
  private String deviceId;
  @JsonFormat(pattern="yyyy-MM-dd HH:mm::ss",timezone = "GMT+8")
  private Date createTime;
  private Integer type;
  private String typeName="合流区";
  private String status="在线";

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
