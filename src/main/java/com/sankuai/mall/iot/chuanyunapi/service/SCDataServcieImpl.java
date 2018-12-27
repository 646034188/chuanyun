package com.sankuai.mall.iot.chuanyunapi.service;

 import com.sankuai.mall.iot.chuanyunapi.dao.data.SCDataDao;
 import com.sankuai.mall.iot.chuanyunapi.dao.data.SCDataFilteDao;
 import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataCondition;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCStatisticInfo;
import com.sankuai.mall.iot.chuanyunapi.enty.Partion;
import com.sankuai.mall.iot.chuanyunapi.enty.SCData;
import com.sankuai.mall.iot.chuanyunapi.util.DateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCDataServcieImpl implements SCDataService {
 @Autowired
 private SCDataDao scDataDao;
 @Autowired
 private SCDataFilteDao scDataFilteDao;
  @Autowired
  private SCDeviceService scDeviceService;
  /* public PageInfo getSCData( Integer poiId,String snList,Integer type,Integer currentPage,Integer numberPerPage) {
    PageInfo pageInfo=new PageInfo();
    List<SCDataInfo> list=new LinkedList<SCDataInfo>();
    Integer totals=scDataDao.getCount(poiId,snList,type, DateUtil.strintToDate("0000-00-00 00:00:00"));

     List<SCData> datas=scDataDao.getInList(poiId,snList,type,DateUtil.strintToDate("0000-00-00 00:00:00"),(currentPage-1)*numberPerPage,numberPerPage);
    for(SCData data:datas){
      SCData item=scDataDao.getOut(data.getHfTagSn(),type,data.getCaptureTime(),scDeviceService.getDeviceSn(data.getSn()));
      SCDataInfo dataInfo=new SCDataInfo();
      dataInfo.setHfTagSn(data.getHfTagSn());
      dataInfo.setFirstTime(data.getCaptureTime());
      if(item==null)
        dataInfo.setEndTime(null);
      else {
        dataInfo.setEndTime(item.getCaptureTime());
        Long time=(dataInfo.getEndTime().getTime()-dataInfo.getFirstTime().getTime())/1000;
        dataInfo.setUseTime(time);
      }
      list.add(dataInfo);

    }
    pageInfo.setList(list);
    if(totals.equals(0)){
      pageInfo.setCurrentPage(0);
      pageInfo.setTotalPage(0);
      return pageInfo;
    }
    pageInfo.setCurrentPage(currentPage);
    pageInfo.setNumberPerPage(numberPerPage);
    Integer  remainder=totals%numberPerPage;
    if(remainder==0)
       pageInfo.setTotalPage(totals/numberPerPage);
    else
      pageInfo.setTotalPage(totals/numberPerPage+1);
    return pageInfo;
  }
*/
  @Override
  public PageInfo getSCDataInfo(SCDataCondition condition) {
    /*
    PageInfo pageInfo=new PageInfo();
    List<SCDataInfo> list=new LinkedList<SCDataInfo>();
    Integer totals=0;

    Integer row=(condition.getCurrentPage()-1)*condition.getNumberPerPage();
    Partion partion=scDeviceService.getPartionInfo(condition.getPoiId(),condition.getLineId(),
        condition.getType());
    //获取入口处监测到的数据
    List<String> entranceList = scDataDao.getEntrance(condition.getPoiId(),partion.getFirstSn(),
        condition.getBeginTime(),condition.getEndTime());

    //输送线
    if(condition.getType()==1) {
      //获取入口处监测到的数据
      List<SCData> datas = scDataDao.getEntranceList(condition.getPoiId(),partion.getFirstSn(),partion.getEndSn(),
          condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());
        totals = scDataDao.getEntranceCount(condition.getPoiId(), partion.getBeginSn(),partion.getEndSn(),
          condition.getBeginTime(), condition.getEndTime());

      for(SCData data:datas){




        //获取入口处监测到的设备通过出口处的信息
        SCData item=scDataDao.getOut(data.getHfTagSn(),data.getCaptureTime(),partion.getEndSn(),
            condition.getEndTime());
        if(item!=null) {
          SCDataInfo dataItem = new SCDataInfo();
          dataItem.setHfTagSn(data.getHfTagSn());
          dataItem.setFirstTime(data.getCaptureTime());
          dataItem.setRegionName("输送线");

          dataItem.setEndTime(item.getCaptureTime());
          Long time = (dataItem.getEndTime().getTime() - dataItem.getFirstTime().getTime()) / 1000;
          dataItem.setUseTime(time);

          list.add(dataItem);
        }

      }
    }
    //电动线或者河流区
    else if(condition.getType()==2||condition.getType()==3){
      //获取入口处监测到的数据
*//*      List<SCData> datas = scDataDao.getEntranceList(condition.getPoiId(),partion.getFirstSn(),
          condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());*//*
      StringBuilder hfTagSnList=new StringBuilder();
      Integer index=0;
      for(String item:entranceList){
        if(index.equals(entranceList.size()-1)) {
          hfTagSnList.append("'").append(item).append("'");
        }else
          hfTagSnList.append("'").append(item).append("',");
        index++;

      }
      if(index==0)
        hfTagSnList.append("''");
      totals=scDataDao.getCount(condition.getPoiId(),partion.getBeginSn(),partion.getEndSn(),hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime());
      //获取区域入口处监测到的数据

      List<SCData> items = scDataDao.getInList(condition.getPoiId(),partion.getBeginSn(),partion.getEndSn(),
          hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());
      for(SCData data:items) {
        //获取入口处监测到的设备通过出口处的信息
        SCData item = scDataDao
            .getOut(data.getHfTagSn(), data.getCaptureTime(), partion.getEndSn(),
                condition.getEndTime());
        if(item!=null){
        SCDataInfo dataItem = new SCDataInfo();
        dataItem.setHfTagSn(data.getHfTagSn());
        dataItem.setFirstTime(data.getCaptureTime());
        if(data.getSn().equals("1MTA201848000010"))
          dataItem.setRegionName("电动线");
        else if(data.getSn().equals("1MTA201848000006"))
          dataItem.setRegionName("合流线");

          dataItem.setEndTime(item.getCaptureTime());
          Long time = (dataItem.getEndTime().getTime() - dataItem.getFirstTime().getTime()) / 1000;
          dataItem.setUseTime(time);
          list.add(dataItem);
        }
      }
    }
    //合计
    else{
         totals =scDataDao.getCountOfEntrance(condition.getPoiId(),partion.getFirstSn(),"1MTA201848000010",
             condition.getBeginTime(),condition.getEndTime());
         StringBuilder hfTagSnList=new StringBuilder();
        Integer index=0;
        for(String item:entranceList){
          if(index.equals(entranceList.size()-1)) {
            hfTagSnList.append("'").append(item).append("'");
          }else
            hfTagSnList.append("'").append(item).append("',");
          index++;

        }
        if(index==0)
          hfTagSnList.append("''");
      //电动线区域的数据
         totals+=scDataDao.getCount(condition.getPoiId(),"1MTA201848000010","1MTA201848000006",hfTagSnList.toString(),
            condition.getBeginTime(),condition.getEndTime());
        //河流区的数据
         totals+=scDataDao.getCount(condition.getPoiId(),"1MTA201848000006","1MTA201848000010",hfTagSnList.toString(),
            condition.getBeginTime(),condition.getEndTime());
         StringBuilder snList=new StringBuilder();
         snList.append("'").append(partion.getFirstSn()).append("',");
         snList.append("'1MTA201848000010','1MTA201848000006'");
        List<SCData> items=scDataDao.getList(condition.getPoiId(),snList.toString(),
            hfTagSnList.toString(),
            condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());

      for(SCData data:items) {
        //获取入口处监测到的设备通过出口处的信息
        SCData item = scDataDao
            .getOut(data.getHfTagSn(), data.getCaptureTime(), partion.getEndSn(),
                condition.getEndTime());
        if(item!=null){
        SCDataInfo dataItem = new SCDataInfo();
        dataItem.setHfTagSn(data.getHfTagSn());
        dataItem.setFirstTime(data.getCaptureTime());
        //设置区域名称
        if(data.getSn().equals("1MTA201848000010"))
          dataItem.setRegionName("电动线");
        else if(data.getSn().equals("1MTA201848000006"))
          dataItem.setRegionName("合流区");
        else
          dataItem.setRegionName("输送线");


          dataItem.setEndTime(item.getCaptureTime());
          Long time = (dataItem.getEndTime().getTime() - dataItem.getFirstTime().getTime()) / 1000;
          dataItem.setUseTime(time);

          list.add(dataItem);

        }
      }

    }
    pageInfo.setList(list);
    if(totals.equals(0)){
      pageInfo.setCurrentPage(0);
      pageInfo.setTotalPage(0);
      return pageInfo;
    }
    pageInfo.setCurrentPage(condition.getCurrentPage());
    pageInfo.setNumberPerPage(condition.getNumberPerPage());
    Integer  remainder=totals%condition.getNumberPerPage();
    if(remainder==0)
      pageInfo.setTotalPage(totals/condition.getNumberPerPage());
    else
      pageInfo.setTotalPage(totals/condition.getNumberPerPage()+1);
    return pageInfo;  }*/
    return null;
  }
  /**
   * 返回每个监控设备的监控率
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  @Override
  public List<SCStatisticInfo> getSCStatisticInfo(Integer poiId, Date beginTime, Date endTime) {
    //上链口的监控设备sn
    String[] entranceSnList={"1MTA201848000001","1MTA201848000002","1MTA201848000003","1MTA201848000004","1MTA201848000005"};
    String[] snList={"1MTA201848000010","1MTA201848000006","1MTA201848000007"};
    String deviceList="'1MTA201848000001','MTA201848000002','1MTA201848000003','1MTA201848000004','1MTA201848000005'";
    List<String> hfTagSnList=scDataFilteDao.getHfTagList(poiId,2,deviceList,beginTime,endTime);
    //设备总数
    Integer totalSize=0;
    List<SCStatisticInfo> result=new LinkedList<>();
    Integer i=1;
    for(String sn:entranceSnList) {
      Integer size = scDataFilteDao.getSizeOfEntrance(poiId,2,sn,beginTime,endTime);
      SCStatisticInfo info=new SCStatisticInfo();
      info.setSn(sn);
      info.setDetectionSize(size);
      info.setTotalSize(size);
       totalSize+=size;
      result.add(info);
    }
    StringBuilder hfsnlist=new StringBuilder();
    Integer index=0;
    for(String item:hfTagSnList){
      if(index.equals(hfTagSnList.size()-1)) {
        hfsnlist.append("'").append(item).append("'");
      }else
        hfsnlist.append("'").append(item).append("',");
      index++;

    }
    if(index==0)
      hfsnlist.append("''");
    for(String sn:snList){
      Integer size = scDataFilteDao.getSize(poiId,2,sn,hfsnlist.toString(),beginTime,endTime);
      SCStatisticInfo info=new SCStatisticInfo();
      info.setSn(sn);
      info.setDetectionSize(size);
      info.setTotalSize(totalSize);
      result.add(info);
    }
    return result;

  }
}
