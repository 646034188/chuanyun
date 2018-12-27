package com.sankuai.mall.iot.chuanyunapi.service;

import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataCondition;
import com.sankuai.mall.iot.chuanyunapi.dto.SCLineStatInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCStatisticInfo;
import java.util.Date;
import java.util.List;

public interface SCDataFilterService {

  public SCLineStatInfo getLineStat(Integer poiId, String firstSn, String endSn, Date beginTime,
      Date endTime);

  public List<SCLineStatInfo> getStatList(Integer poiId, Date beginTime, Date endTime);

  /**
   * 分页获取载具通过某一段的时间，包括起始时间，离开时间和耗时
   */
  public PageInfo getSCDataInfo(SCDataCondition condition);

  /**
   * 保存某一段时间内的耗时相关指标
   */
  public List<SCLineStatInfo> saveSCStatistics(Integer poiId, Date beginTime, Date endTime);

  /**
   * 统计某一段时间内的耗时相关指标
   */
  public List<SCLineStatInfo> getStatisData(Integer poiId, Date beginTime, Date endTime);

  /**
   * 返回每个监控设备的监控率(从数据库读取)
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  List<SCStatisticInfo> getSCStatisticInfo(Integer poiId, Date beginTime,Date endTime);

  /**
   * 保存每个监控设备的监控率到缓存
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  List<SCStatisticInfo> saveSCStatisticInfo(Integer poiId, Date beginTime,Date endTime);

  /**
   * 返回每个监控设备的监控率
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  List<SCStatisticInfo> getSCDeviceStatistcis(Integer poiId,Date beginTime,Date endTime);

  }
