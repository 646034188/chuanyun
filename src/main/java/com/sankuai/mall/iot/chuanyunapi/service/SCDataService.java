package com.sankuai.mall.iot.chuanyunapi.service;

import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataCondition;
import com.sankuai.mall.iot.chuanyunapi.dto.SCStatisticInfo;
import java.util.Date;
import java.util.List;

public interface SCDataService {
    PageInfo getSCDataInfo(SCDataCondition condition);

    /**
     * 返回每个监控设备的监控率
     * @param poiId
     * @param beginTime
     * @param endTime
     * @return
     */
    List<SCStatisticInfo> getSCStatisticInfo(Integer poiId, Date beginTime,Date endTime);
}
