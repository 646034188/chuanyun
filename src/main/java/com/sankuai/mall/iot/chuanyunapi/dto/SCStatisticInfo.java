package com.sankuai.mall.iot.chuanyunapi.dto;

import java.io.Serializable;

public class SCStatisticInfo implements Serializable {
  private Integer lineId;
  private Integer lineName;
  private String sn;
  private Integer detectionSize; //监测设备监测到的HF标签总和
  private Integer totalSize;     //所有HF标签总和

  public Integer getLineId() {
    return lineId;
  }

  public void setLineId(Integer lineId) {
    this.lineId = lineId;
  }

  public Integer getLineName() {
    return lineName;
  }

  public void setLineName(Integer lineName) {
    this.lineName = lineName;
  }

  public String getSn() {
    return sn;
  }

  public void setSn(String sn) {
    this.sn = sn;
  }

  public Integer getDetectionSize() {
    return detectionSize;
  }

  public void setDetectionSize(Integer detectionSize) {
    this.detectionSize = detectionSize;
  }

  public Integer getTotalSize() {
    return totalSize;
  }

  public void setTotalSize(Integer totalSize) {
    this.totalSize = totalSize;
  }
}
