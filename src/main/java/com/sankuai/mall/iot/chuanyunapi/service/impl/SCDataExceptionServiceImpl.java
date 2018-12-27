package com.sankuai.mall.iot.chuanyunapi.service.impl;

import com.sankuai.mall.iot.chuanyunapi.dao.data.SCDataExceptionDao;
import com.sankuai.mall.iot.chuanyunapi.po.SCExceptionData;
import com.sankuai.mall.iot.chuanyunapi.service.SCDataExceptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCDataExceptionServiceImpl implements SCDataExceptionService {
  @Autowired
  private SCDataExceptionDao scDataExceptionDao;

  @Override
  public void saveExceptionData(SCExceptionData data) {
    scDataExceptionDao.insert(data);
  }

  /**
   * 更新异常数据
   * @param data
   */
  public void updateExceptionData(SCExceptionData data){
    scDataExceptionDao.update(data);
  }

  /**
   * 查询data_id字段为dataID的记录总数
   * @param dataID
   * @return
   */
  public Integer getCount(Integer dataID){
    return scDataExceptionDao.getCount(dataID);
  }
}
