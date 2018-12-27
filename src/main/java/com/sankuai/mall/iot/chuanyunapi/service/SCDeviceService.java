package com.sankuai.mall.iot.chuanyunapi.service;

import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.enty.Partion;

public interface SCDeviceService {
  /**
   *获取分段信息
   */
  Partion getPartionInfo(Integer poiId,Integer lineId,Integer type);

  /**
   * 获取设备的device_sn
   * @param sn
   * @return
   */
  String  getDeviceSn(String sn );

  /**
   * 获取门店内的所有设备信息
   * @param poiId
   * @param type
   * @param currentPage
   * @param numberPerPage
   * @return
   */
  PageInfo getSCDevice(Integer poiId,Integer type, Integer currentPage ,Integer numberPerPage);

  /**判断该监控设备是否在上链口
   * @param sn
   * @return
   */
  boolean isFirstNode(String sn,Integer poiId);

  /**
   * 判断preSN是否是sn之前的设备
   * @param sn
   * @param preSn
   * @param poiId
   * @return
   */
  boolean isPre(String sn,String preSn,Integer poiId);


 }
