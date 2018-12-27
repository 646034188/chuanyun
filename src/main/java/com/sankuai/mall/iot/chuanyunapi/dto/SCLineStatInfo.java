package com.sankuai.mall.iot.chuanyunapi.dto;

import java.io.Serializable;

/**
 * 线路某一段的时间统计信息
 */
public class SCLineStatInfo implements Serializable {
  private String firstSn;
  private String endSn;
  private Integer num;//经过该区域的载具数
  private String lineName;
  private Long maxTime;
  private Long minTime;
  private Long averageTime;
  private Long totalTime;
  private String maxTimeSn; //耗时最长的载具sn
  private String minTimeSn; //耗时最短的载具sn

  public String getFirstSn() {
    return firstSn;
  }

  public void setFirstSn(String firstSn) {
    this.firstSn = firstSn;
  }

  public String getEndSn() {
    return endSn;
  }

  public void setEndSn(String endSn) {
    this.endSn = endSn;
  }

  public String getLineName() {
    return lineName;
  }

  public void setLineName(String lineName) {
    this.lineName = lineName;
  }

  public Long getMaxTime() {
    return maxTime;
  }

  public void setMaxTime(Long maxTime) {
    this.maxTime = maxTime;
  }

  public Long getMinTime() {
    return minTime;
  }

  public void setMinTime(Long minTime) {
    this.minTime = minTime;
  }

  public Long getAverageTime() {
    return averageTime;
  }

  public void setAverageTime(Long averageTime) {
    this.averageTime = averageTime;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public Long getTotalTime() {
    return totalTime;
  }

  public void setTotalTime(Long totalTime) {
    this.totalTime = totalTime;
  }

  public String getMaxTimeSn() {
    return maxTimeSn;
  }

  public void setMaxTimeSn(String maxTimeSn) {
    this.maxTimeSn = maxTimeSn;
  }

  public String getMinTimeSn() {
    return minTimeSn;
  }

  public void setMinTimeSn(String minTimeSn) {
    this.minTimeSn = minTimeSn;
  }
}
