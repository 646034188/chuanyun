package com.sankuai.mall.iot.chuanyunapi.service;

import com.sankuai.mall.iot.chuanyunapi.dao.device.SCDeviceDao;
import com.sankuai.mall.iot.chuanyunapi.dto.Device;
import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.enty.Partion;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCDeviceServiceImpl implements  SCDeviceService{
  @Autowired
  SCDeviceDao scDeviceDao;

  @Override
  public Partion getPartionInfo(Integer poiId,Integer lineId,Integer type) {
    StringBuilder list=new StringBuilder();
    Partion partion=new Partion();
    if(poiId.equals(28)) {
      list.append("'1MTA20184400001',");
      list.append("'1MTA20184400003',");
      list.append("'1MTA20184400005',");
      list.append("'1MTA20184400007',");
      list.append("'1MTA20184400009',");
      list.append("'1MTA201844000011'");
    }else if(poiId.equals(18)){
       if(lineId==1){
        //输送线
        if(type==1){
          partion.setBeginSn("1MTA201848000001");
          partion.setEndSn("1MTA201848000010");
          partion.setFirstSn("1MTA201848000001");
          //电动线
        }else if(type==2){
          partion.setBeginSn("1MTA201848000010");
          partion.setEndSn("1MTA2018480000006");
          partion.setFirstSn("1MTA201848000001");
          //河流区
        }else if(type==3){
          partion.setBeginSn("1MTA201848000006");
          partion.setEndSn("1MTA201848000007");
          partion.setFirstSn("1MTA201848000001");
        }else
          partion.setFirstSn("1MTA201848000001");
      }else if(lineId==2){
         //输送线
         if(type==1) {
           partion.setBeginSn("1MTA201848000002");
           partion.setEndSn("1MTA201848000010");
           partion.setFirstSn("1MTA201848000002");
           //电动线
         }else if(type==2){
           partion.setBeginSn("1MTA201848000010");
           partion.setEndSn("1MTA201848000006");
           partion.setFirstSn("1MTA201848000002");
           //河流区
         }else if(type==3){
           partion.setBeginSn("1MTA201848000006");
           partion.setEndSn("1MTA201848000007");
           partion.setFirstSn("1MTA201848000002");
         }else
           partion.setFirstSn("1MTA201848000002");

      }else if(lineId==3){
         {
           //输送线
           if(type==1) {
             partion.setBeginSn("1MTA201848000003");
             partion.setEndSn("1MTA201848000010");
             partion.setFirstSn("1MTA201848000003");
             //电动线
           }else if(type==2){
             partion.setBeginSn("1MTA201848000010");
             partion.setEndSn("1MTA201848000006");
             partion.setFirstSn("1MTA201848000003");
             //河流区
           }else if(type==3){
             partion.setBeginSn("1MTA201848000006");
             partion.setEndSn("1MTA201848000007");
             partion.setFirstSn("1MTA201848000003");
           }else
             partion.setFirstSn("1MTA201848000003");


         }

      }else if(lineId==4){
         {
           {
             //输送线
             if(type==1) {
               partion.setBeginSn("1MTA201848000004");
               partion.setEndSn("1MTA201848000010");
               partion.setFirstSn("1MTA201848000004");
               //电动线
             }else if(type==2){
               partion.setBeginSn("1MTA201848000010");
               partion.setEndSn("1MTA201848000006");
               partion.setFirstSn("1MTA201848000004");
               //河流区
             }else if(type==3){
               partion.setBeginSn("1MTA201848000006");
               partion.setEndSn("1MTA201848000007");
               partion.setFirstSn("1MTA201848000004");
             }else
               partion.setFirstSn("1MTA201848000004");

           }

         }

      }else if(lineId==5){
         {
           {
             //输送线
             if(type==1) {
               partion.setBeginSn("1MTA201848000005");
               partion.setEndSn("1MTA201848000010");
               partion.setFirstSn("1MTA201848000005");
               //电动线
             }else if(type==2){
               partion.setBeginSn("1MTA201848000010");
               partion.setEndSn("1MTA201848000006");
               partion.setFirstSn("1MTA201848000005");
               //河流区
             }else if(type==3){
               partion.setBeginSn("1MTA201848000006");
               partion.setEndSn("1MTA201848000007");
               partion.setFirstSn("1MTA201848000005");
             }else
               partion.setFirstSn("1MTA201848000005");

           }

         }

      }

    }
    return partion;
  }
  @Override
  public String  getDeviceSn(String sn ){
    Map<String,String > map=new HashMap<>();
    map.put("1MTA20184400001","1MTA20184400002");
    map.put("1MTA20184400003","1MTA20184400004");
    map.put("1MTA20184400005","1MTA20184400006");
    map.put("1MTA20184400007","1MTA20184400008");
    map.put("1MTA20184400009","1MTA201844000010");
    map.put("1MTA201844000011","1MTA201844000012");

    map.put("1MTA201848000001","1MTA201848000010");
    map.put("1MTA201848000002","1MTA201848000010");
    map.put("1MTA201848000003","1MTA201848000010");
    map.put("1MTA201848000004","1MTA201848000010");
    map.put("1MTA201848000005","1MTA201848000010");
    map.put("1MTA201848000006","1MTA201848000007");
    map.put("1MTA201848000010","1MTA201848000006");
    return map.get(sn);

  }
  @Override
  public PageInfo getSCDevice(Integer poiId,Integer type, Integer currentPage ,Integer numberPerPage){
    PageInfo pageInfo=new PageInfo();
    Integer  totals=scDeviceDao.getCout(poiId,type);
    List<Device> deviceList=scDeviceDao.getDeviceList(poiId,type,(currentPage-1)*numberPerPage,numberPerPage);
    pageInfo.setCurrentPage(currentPage);
    pageInfo.setNumberPerPage(numberPerPage);
    Integer remainder=totals%numberPerPage;
    if(remainder==0)
      pageInfo.setTotalPage(totals/numberPerPage);
    else
      pageInfo.setTotalPage(totals/numberPerPage+1);
    pageInfo.setList(deviceList);
    return pageInfo;
  }
  @Override
  public boolean isFirstNode(String sn,Integer poiId){
    if(poiId==18){
      if(sn.equals("1MTA201848000001")||sn.equals("1MTA201848000002")||sn.equals("1MTA201848000003")||sn.equals("1MTA201848000004")||sn.equals("1MTA201848000005")){
        return true;
      }
      else return false;
    }
    else{
      return false;
    }
  }
  /**
   * 判断preSN是否是sn之前的设备
   * @param sn
   * @param preSn
   * @param poiId
   * @return
   */
  public boolean isPre(String sn,String preSn,Integer poiId){
   return true;
  }




}
