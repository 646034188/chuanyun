package com.sankuai.mall.iot.chuanyunapi.api.controller;

import com.sankuai.mall.iot.chuanyunapi.api.result.Response;
import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataCondition;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataStatisticCondition;
import com.sankuai.mall.iot.chuanyunapi.dto.SCLineStatInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCStatisticInfo;
import com.sankuai.mall.iot.chuanyunapi.service.SCDataFilterService;
import com.sankuai.mall.iot.chuanyunapi.service.SCDataService;
import com.sankuai.mall.iot.chuanyunapi.service.SCDeviceService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/iot")
public class SCDataController {
  @Autowired
  private SCDataService scDataservice;
  @Autowired
  private SCDataFilterService scDataFilterService;
  @Autowired
  private SCDeviceService scDeviceService;

  /**
   *
   * @param currentPage 当前页码
   * @param numberPerPage 每页显示的数据量
   * @param poid 门店ID
   * @param type 悬挂链区域
   * @return
   */
  @RequestMapping(value = "/scdata")
  public Response getSCData(SCDataCondition condition){

    Response result=new Response();
    PageInfo pageInfo=scDataFilterService.getSCDataInfo(condition);
    result.setData(pageInfo);
    result.setCode("400");
    return result;
  }

  /**
   * 获取每个监控设备的监控率
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  @RequestMapping(value = "/scDeviceStat")
  public Response getDeviceStat(SCDataStatisticCondition condition){

    Response result=new Response();
    List<SCStatisticInfo> data= scDataFilterService.getSCDeviceStatistcis(condition.getPoiId(),condition.getBeginTime(),condition.getEndTime());
    result.setData(data);
    result.setCode("400");
    return result;
  }

  /**
   * 获取每条线路的耗时
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  @RequestMapping(value = "/scLineStat")
  public Response getLineStat(SCDataStatisticCondition condition){

    Response result=new Response();
    List<SCLineStatInfo> data= scDataFilterService.getStatList(condition.getPoiId(),condition.getBeginTime(),condition.getEndTime());
    result.setData(data);
    result.setCode("400");
    return result;
  }


  /**
   * 保存每条线路的耗时到缓存中
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  @RequestMapping(value = "/saveLineStat")
  public Response saveLineStat(SCDataStatisticCondition condition){
    Response result=new Response();
    List<SCLineStatInfo> data=scDataFilterService.saveSCStatistics(condition.getPoiId(),condition.getBeginTime(),condition.getEndTime());
    result.setData(data);
    result.setCode("400");
    return result;
  }

  /**
   * 保存每条线路的耗时到缓存中
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  @RequestMapping(value = "/saveDeviceStat")
  public Response saveDeviceStat(SCDataStatisticCondition condition){
    Response result=new Response();
    List<SCStatisticInfo> data=scDataFilterService.saveSCStatisticInfo(condition.getPoiId(),condition.getBeginTime(),condition.getEndTime());
    result.setData(data);
    result.setCode("400");
    return result;
  }

}
