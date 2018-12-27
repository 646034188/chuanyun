package com.sankuai.mall.iot.chuanyunapi.enty;

/**
 * 悬挂链分段
 */
public class Partion {
  private String beginSn; //分段入口监控设备
  private String endSn;   //分段出口设备
  private Integer lineId; //分段所属线路
  private String firstSn; //线路上链口设备

  public String getBeginSn() {
    return beginSn;
  }

  public void setBeginSn(String beginSn) {
    this.beginSn = beginSn;
  }

  public String getEndSn() {
    return endSn;
  }

  public void setEndSn(String endSn) {
    this.endSn = endSn;
  }

  public Integer getLineId() {
    return lineId;
  }

  public void setLineId(Integer lineId) {
    this.lineId = lineId;
  }

  public String getFirstSn() {
    return firstSn;
  }

  public void setFirstSn(String firstSn) {
    this.firstSn = firstSn;
  }
}
