package com.sankuai.mall.iot.chuanyunapi.service;

import com.sankuai.mall.iot.chuanyunapi.po.SCExceptionData;

public interface SCDataExceptionService {

  /**
   * 保存异常数据
   * @param data
   */
  void saveExceptionData(SCExceptionData data);

  /**
   * 更新异常数据
   * @param data
   */
  void updateExceptionData(SCExceptionData data);

  /**
   * 查询data_id字段为dataID的记录总数
   * @param dataID
   * @return
   */
  Integer getCount(Integer dataID);


}
