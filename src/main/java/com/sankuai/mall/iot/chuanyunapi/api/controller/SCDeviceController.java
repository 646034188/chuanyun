package com.sankuai.mall.iot.chuanyunapi.api.controller;

import com.sankuai.mall.iot.chuanyunapi.api.result.Response;
import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.service.SCDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SCDeviceController {
  @Autowired
  private SCDeviceService scDeviceService;
  @RequestMapping(value = "/iot/scdevice")
  public Response getSCData(Integer currentPage,Integer numberPerPage,Integer poiId,Integer type){

    Response result=new Response();
    PageInfo pageInfo=scDeviceService.getSCDevice(poiId,type,currentPage,numberPerPage);
    result.setData(pageInfo);
    result.setCode("400");
    return result;
  }

}
