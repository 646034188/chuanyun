package com.sankuai.mall.iot.chuanyunapi.service;

import com.dianping.squirrel.client.StoreKey;
import com.dianping.squirrel.client.impl.redis.RedisStoreClient;
import com.sankuai.mall.iot.chuanyunapi.dao.data.SCDataDao;
import com.sankuai.mall.iot.chuanyunapi.dao.data.SCDataFilteDao;
import com.sankuai.mall.iot.chuanyunapi.dto.PageInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataCondition;
import com.sankuai.mall.iot.chuanyunapi.dto.SCDataInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCLineStatInfo;
import com.sankuai.mall.iot.chuanyunapi.dto.SCStatisticInfo;
import com.sankuai.mall.iot.chuanyunapi.enty.Partion;
import com.sankuai.mall.iot.chuanyunapi.enty.SCData;
import com.sankuai.mall.iot.chuanyunapi.enty.SCFilterData;
import com.sankuai.mall.iot.chuanyunapi.po.SCExceptionData;
import com.sankuai.mall.iot.chuanyunapi.util.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SCDataFilterServiceImpl implements SCDataFilterService{
  private static final Logger logger = LoggerFactory.getLogger(SCDataFilterServiceImpl.class);

  @Autowired
  private SCDataFilteDao scDataFilteDao;
  @Autowired
  private SCDeviceService scDeviceService;
  @Autowired
  private SCDataExceptionService scDataExceptionService;
  @Autowired
  private RedisStoreClient redisStoreClient;

  /**
   * 分页获取载具通过某一段的时间，包括起始时间，离开时间和耗时
   * @param condition
   * @return
   */
  public PageInfo getSCDataInfo(SCDataCondition condition){
    PageInfo pageInfo=new PageInfo();
    List<SCDataInfo> list=new LinkedList<>();
    Integer totals=0;

    Integer row=(condition.getCurrentPage()-1)*condition.getNumberPerPage();
    Partion partion=scDeviceService.getPartionInfo(condition.getPoiId(),condition.getLineId(),
        condition.getType());


    //输送线
    if(condition.getType()==1) {
      //获取入口处监测到的数据
      List<SCData> datas = scDataFilteDao.getList(condition.getPoiId(),partion.getFirstSn(),partion.getEndSn(),
          condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());
      totals = scDataFilteDao.getCountOfEntrance(condition.getPoiId(), partion.getBeginSn(),partion.getEndSn(),
          condition.getBeginTime(), condition.getEndTime());

      for(SCData data:datas){
        //获取入口处监测到的设备通过出口处的信息
        Date item=scDataFilteDao.getOut(data.getHfTagSn(),data.getCaptureTime(),partion.getEndSn(),
            condition.getEndTime());
        if(item!=null) {
          SCDataInfo dataItem = new SCDataInfo();
          dataItem.setHfTagSn(data.getHfTagSn());
          dataItem.setFirstTime(data.getCaptureTime());
          dataItem.setRegionName("输送线");
          dataItem.setEndTime(item);
          Long time = (dataItem.getEndTime().getTime() - dataItem.getFirstTime().getTime()) / 1000;
          if(time>200){
            time=null;
            dataItem.setEndTime(null);
          }
          dataItem.setUseTime(time);

          list.add(dataItem);
        }

      }
    }
    //电动线或者河流区
    else if(condition.getType()==2||condition.getType()==3){
       //获取入口处监测到的数据
      List<String> entranceList = scDataFilteDao.getEntrance(condition.getPoiId(),partion.getFirstSn(),
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
      totals=scDataFilteDao.getCount(condition.getPoiId(),partion.getBeginSn(),partion.getEndSn(),hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime());
      //获取区域入口处监测到的数据

      List<SCData> items = scDataFilteDao.getInList(condition.getPoiId(),partion.getBeginSn(),partion.getEndSn(),
          hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());
      for(SCData data:items) {
        //获取入口处监测到的设备通过出口处的信息
        Date item = scDataFilteDao
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
          dataItem.setEndTime(item);
          Long time = (dataItem.getEndTime().getTime() - dataItem.getFirstTime().getTime()) / 1000;
          if(time>200) {
            dataItem.setEndTime(null);
            time = null;
          }
           dataItem.setUseTime(time);
          list.add(dataItem);
        }
      }
    }
    //合计
    else{
      totals =scDataFilteDao.getCountOfEntrance(condition.getPoiId(),partion.getFirstSn(),"1MTA201848000010",
          condition.getBeginTime(),condition.getEndTime());
      List<String> entranceList = scDataFilteDao.getEntrance(condition.getPoiId(),partion.getFirstSn(),
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
      totals+=scDataFilteDao.getCount(condition.getPoiId(),"1MTA201848000010","1MTA201848000006",hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime());
      //河流区的数据
      totals+=scDataFilteDao.getCount(condition.getPoiId(),"1MTA201848000006","1MTA201848000010",hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime());
      StringBuilder snList=new StringBuilder();
      snList.append("'").append(partion.getFirstSn()).append("',");
      snList.append("'1MTA201848000010','1MTA201848000006'");
      List<SCData> items=scDataFilteDao.getTotalList(condition.getPoiId(),snList.toString(),
          hfTagSnList.toString(),
          condition.getBeginTime(),condition.getEndTime(),row,condition.getNumberPerPage());

      for(SCData data:items) {
        //获取入口处监测到的设备通过出口处的信息
        Date item = scDataFilteDao
            .getOut(data.getHfTagSn(), data.getCaptureTime(), scDeviceService.getDeviceSn(data.getSn()),
                condition.getEndTime());

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
          if(item!=null) {
            dataItem.setEndTime(item);

            Long time =
                (dataItem.getEndTime().getTime() - dataItem.getFirstTime().getTime()) / 1000;
            if (time > 200) {
              dataItem.setEndTime(null);
              time = null;
            }
            dataItem.setUseTime(time);
          }else{
            dataItem.setEndTime(item);
          }
        list.add(dataItem);
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
    return pageInfo;

  }
  /**
   * 线路某一段的时间统计信息
   * @param poiId
   * @param firstSn
   * @param endSn
   * @return
   */
   public SCLineStatInfo getLineStat(Integer poiId,String firstSn,String endSn, Date beginTime,Date endTime){
     SCLineStatInfo result=new SCLineStatInfo();
     List<SCFilterData> lineInfos=scDataFilteDao.getSCDataList(poiId,2,firstSn,beginTime,endTime);
     Long max=0L;
     Long min=1000L;
     Integer size=0;
     Long total=0L;
      String hfTagSn="";
     for(SCFilterData info:lineInfos){
       if(info.getHfTagSn().equals(hfTagSn)){
         continue;
       }
       hfTagSn=info.getHfTagSn();
       SCFilterData out= scDataFilteDao.getSCData(poiId,2,endSn,info.getHfTagSn(),info.getLastCaptureTime(),endTime);
       if(out!=null){

         long time= DateUtil.getBetween(out.getFirstCaptureTime(),info.getLastCaptureTime());
            //如果时间间隔相差100就剔除
         if(time>200L)
           continue;
         if(time>max)
           max=time;
         if(time<min)
           min=time;
         size++;
         total+=time;
       }
     }
     if(min==1000)
       min=0L;
     result.setMinTime(min);
     result.setMaxTime(max);
     result.setNum(size);
     if(size==0)
       result.setAverageTime(0L);
     else
       result.setAverageTime(total/size);
     return result;
   }

  /**
   * 获取门店所有线路的时间统计信息
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
   public List<SCLineStatInfo> getStatList(Integer poiId,Date beginTime,Date endTime){
     SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
     String field=format.format(beginTime);
     List<List<SCLineStatInfo>> caches=null;
     //如果是今天之前的数据，从缓存读取
     if(!field.equals(format.format(new Date()))) {
       String category = "iot_sc_statistics";
       String key = poiId.toString();
       StoreKey storeKey = new StoreKey(category, key);
       caches = redisStoreClient.hmget(storeKey, field);
       if(caches.get(0)==null){
         logger.error("poiId:"+poiId+";"+field+"无法获取缓存的统计数据");
       }else {
         return caches.get(0);
       }
     }
      //如果无法从缓存获取数据，直接从数据库读取
     List<String[]> lineList=new LinkedList<>();
     String[] item1={"1MTA201848000001","1MTA201848000010"};
     lineList.add(item1);
     String[] item2={"1MTA201848000002","1MTA201848000010"};
     lineList.add(item2);
     String[] item3={"1MTA201848000003","1MTA201848000010"};
     lineList.add(item3);
     String[] item4={"1MTA201848000004","1MTA201848000010"};
     lineList.add(item4);
     String[] item5={"1MTA201848000005","1MTA201848000010"};
     lineList.add(item5);

     String[] item6={"1MTA201848000010","1MTA201848000006"};
     lineList.add(item6);
     String[] item7={"1MTA201848000006","1MTA201848000007"};
     lineList.add(item7);

     List<SCLineStatInfo> result=new LinkedList<>();
     for(String[] item:lineList){
       result.add(getLineStat(poiId,item[0],item[1],beginTime,endTime));
     }
     return result;
   }

  /**
   * 统计某一段时间内的耗时相关指标
   * @param poiId
   * @param beginTime
   * @param endTime
   */
  @Override
  public List<SCLineStatInfo> getStatisData(Integer poiId,Date beginTime,Date endTime){
    List<SCLineStatInfo> result=new LinkedList<>();
    List<String> tagList = scDataFilteDao.queryDistinctHfTagSn(poiId, beginTime, endTime);
    Map<String,SCLineStatInfo> map=getAllLine(poiId);
    Integer id=0;
    String sn=null;
    String hfTagSN=null;
     for(String hfTagSn:tagList){
      List<SCFilterData> dataList=scDataFilteDao.queryByHfTagSn(poiId,hfTagSn,beginTime,endTime);
      //是否被上链口监控设备扫描到
      boolean hasFirst=false;
      for(int i=0;i<dataList.size()-1;i++){
        SCFilterData currentNode=dataList.get(i);
        SCFilterData nextNode=dataList.get(i+1);
        String first=currentNode.getSn();
        String second=nextNode.getSn();
        SCExceptionData data=new SCExceptionData();
        //遍历到上链口扫描到的数据
        if(!scDeviceService.isFirstNode(first,poiId)&&!hasFirst){
          continue;
        }else if(scDeviceService.isFirstNode(first,poiId)){
          hasFirst=true;
        }
        if(second.equals("1MTA201848000007")){
          hasFirst=false;
        }
/*        if(first.equals("1MTA201848000006")&&!second.equals("1MTA201848000007")){
          System.err.println("hfTagSn:"+currentNode.getHfTagSn()+"firstsn:"+first+"endSn:"+second);
        }*/
        //
        //第i和i+1个数据的sn是某一小段的起始点和结束点
        if(second!=null&&second.equals(scDeviceService.getDeviceSn(first))){
          Date firstTime=null;
          Date lastTime=null;
          if(scDeviceService.isFirstNode(first,poiId)) {
            firstTime = currentNode.getLastCaptureTime();
          }
          else {
            firstTime = currentNode.getFirstCaptureTime();
          }
          if(second.equals("1MTA201848000007")){
            lastTime= nextNode.getLastCaptureTime();
          }else{
            lastTime=nextNode.getFirstCaptureTime();
          }

          long time=DateUtil.getBetween(lastTime,firstTime);
          if(time<200&&time>1) {
            SCLineStatInfo info = map.get(currentNode.getSn());
            if (time > info.getMaxTime()) {
              info.setMaxTime(time);
              info.setMaxTimeSn(currentNode.getHfTagSn());
            }
            if (time < info.getMinTime()) {
              info.setMinTime(time);
              info.setMinTimeSn(currentNode.getHfTagSn());
            }
            info.setTotalTime(info.getTotalTime() + time);
            info.setNum(info.getNum() + 1);
          } else if(time<=1){
            data.setDataId(currentNode.getId());
            data.setSn(currentNode.getSn());
            data.setHfTagSn(currentNode.getHfTagSn());
            data.setFirstCaptureTime(currentNode.getFirstCaptureTime());
            data.setExceptionType(3);
            data.setExceptionTypeName("耗时为"+time+"s小于等于1s!");
          }else{
            //打印超过200s的记录
            data.setDataId(currentNode.getId());
            data.setSn(currentNode.getSn());
            data.setHfTagSn(currentNode.getHfTagSn());
            data.setFirstCaptureTime(currentNode.getFirstCaptureTime());
            data.setExceptionType(2);
            data.setExceptionTypeName("耗时为"+time+"s，超过200s");
          }
        }else if(scDeviceService.isFirstNode(second,poiId)&&scDeviceService.isFirstNode(first,poiId)){ //上链口误扫描的数据
          data.setDataId(currentNode.getId());
          data.setSn(currentNode.getSn());
          data.setHfTagSn(currentNode.getHfTagSn());
          data.setFirstCaptureTime(currentNode.getFirstCaptureTime());
          data.setExceptionType(4);
          data.setExceptionTypeName("上链口误扫描!");
        }
        else if(scDeviceService.isFirstNode(second,poiId)&&i==(dataList.size()-2)){
          //上链口误扫描到的数据
            data.setDataId(nextNode.getId());
            data.setSn(nextNode.getSn());
            data.setHfTagSn(nextNode.getHfTagSn());
            data.setFirstCaptureTime(nextNode.getFirstCaptureTime());
            data.setExceptionType(4);
            data.setExceptionTypeName("上链口误扫描!");
        }
        else if(!first.equals("1MTA201848000007")){
          //打印无法找到出口记录的数据
          data.setDataId(currentNode.getId());
          data.setSn(currentNode.getSn());
          data.setHfTagSn(currentNode.getHfTagSn());
          data.setFirstCaptureTime(currentNode.getFirstCaptureTime());
          data.setExceptionType(1);
          data.setExceptionTypeName("无法找到对应的出口记录!");

        }
        if(data.getDataId()!=null){
          //更新数据
          if(scDataExceptionService.getCount(data.getDataId())>0){
            scDataExceptionService.updateExceptionData(data);
          }else { //新增数据
            scDataExceptionService.saveExceptionData(data);
          }
        }
      }
    }
     //设置每小段区域统计数据的平均耗时
    for(Map.Entry<String,SCLineStatInfo> enty:map.entrySet()){
      long num=enty.getValue().getNum();
      long totals=enty.getValue().getTotalTime();
      if(num==0)
        enty.getValue().setAverageTime(0L);
      else
        enty.getValue().setAverageTime(totals/num);
      if(enty.getValue().getMaxTime()<=enty.getValue().getMinTime()){
        enty.getValue().setMinTime(enty.getValue().getMaxTime());
      }
    }
    result.add(map.get("1MTA201848000001"));
    result.add(map.get("1MTA201848000002"));
    result.add(map.get("1MTA201848000003"));
    result.add(map.get("1MTA201848000004"));
    result.add(map.get("1MTA201848000005"));
    result.add(map.get("1MTA201848000010"));
    result.add(map.get("1MTA201848000006"));
    return result;
  }

  public Map<String,SCLineStatInfo> getAllLine(Integer poidId){
    Map<String,SCLineStatInfo> lines=new HashMap<>();
    SCLineStatInfo info=new SCLineStatInfo();
    info.setFirstSn("1MTA201848000001");
    info.setEndSn("1MTA201848000010");
    info.setMaxTime(0L);
    info.setMinTime(1000L);
    info.setNum(0);
    info.setTotalTime(0L);
    lines.put("1MTA201848000001",info);

    SCLineStatInfo info1=new SCLineStatInfo();
    info1.setFirstSn("1MTA201848000002");
    info1.setEndSn("1MTA201848000010");
    info1.setMaxTime(0L);
    info1.setMinTime(1000L);
    info1.setNum(0);
    info1.setTotalTime(0L);

    lines.put("1MTA201848000002",info1);

    SCLineStatInfo info2=new SCLineStatInfo();
    info2.setFirstSn("1MTA201848000003");
    info2.setEndSn("1MTA201848000010");
    info2.setMaxTime(0L);
    info2.setMinTime(1000L);
    info2.setNum(0);
    info2.setTotalTime(0L);

    lines.put("1MTA201848000003",info2);

    SCLineStatInfo info3=new SCLineStatInfo();
    info3.setFirstSn("1MTA201848000004");
    info3.setEndSn("1MTA201848000010");
    info3.setMaxTime(0L);
    info3.setMinTime(1000L);
    info3.setNum(0);
    info3.setTotalTime(0L);

    lines.put("1MTA201848000004",info3);

    SCLineStatInfo info4=new SCLineStatInfo();
    info4.setFirstSn("1MTA201848000005");
    info4.setEndSn("1MTA201848000010");
    info4.setMaxTime(0L);
    info4.setMinTime(1000L);
    info4.setNum(0);
    info4.setTotalTime(0L);
    lines.put("1MTA201848000005",info4);

    SCLineStatInfo info5=new SCLineStatInfo();
    info5.setFirstSn("1MTA201848000010");
    info5.setEndSn("1MTA201848000006");
    info5.setMaxTime(0L);
    info5.setMinTime(1000L);
    info5.setNum(0);
    info5.setTotalTime(0L);
    lines.put("1MTA201848000010",info5);

    SCLineStatInfo info6=new SCLineStatInfo();
    info6.setFirstSn("1MTA201848000006");
    info6.setEndSn("1MTA201848000007");
    info6.setMaxTime(0L);
    info6.setMinTime(1000L);
    info6.setNum(0);
    info6.setTotalTime(0L);

    lines.put("1MTA201848000006",info6);

    return lines;
  }


  public Map<String,SCStatisticInfo> getAllNode(Integer poidId){
    Map<String,SCStatisticInfo> nodes=new HashMap<>();
    SCStatisticInfo info=new SCStatisticInfo();
    info.setSn("1MTA201848000001");
    info.setTotalSize(0);
    info.setDetectionSize(0);
    nodes.put("1MTA201848000001",info);

    SCStatisticInfo info1=new SCStatisticInfo();
    info1.setSn("1MTA201848000002");
    info1.setTotalSize(0);
    info1.setDetectionSize(0);
    nodes.put("1MTA201848000002",info1);

    SCStatisticInfo info2=new SCStatisticInfo();
    info2.setSn("1MTA201848000003");
    info2.setTotalSize(0);
    info2.setDetectionSize(0);
    nodes.put("1MTA201848000003",info2);

    SCStatisticInfo info3=new SCStatisticInfo();
    info3.setSn("1MTA201848000004");
    info3.setTotalSize(0);
    info3.setDetectionSize(0);
    nodes.put("1MTA201848000004",info3);

    SCStatisticInfo info4=new SCStatisticInfo();
    info4.setSn("1MTA201848000005");
    info4.setTotalSize(0);
    info4.setDetectionSize(0);
    nodes.put("1MTA201848000005",info4);

    SCStatisticInfo info5=new SCStatisticInfo();
    info5.setSn("1MTA201848000010");
    info5.setTotalSize(0);
    info5.setDetectionSize(0);
    nodes.put("1MTA201848000010",info5);

    SCStatisticInfo info6=new SCStatisticInfo();
    info6.setSn("1MTA201848000006");
    info6.setTotalSize(0);
    info6.setDetectionSize(0);
    nodes.put("1MTA201848000006",info6);

    SCStatisticInfo info7=new SCStatisticInfo();
    info7.setSn("1MTA201848000007");
    info7.setTotalSize(0);
    info7.setDetectionSize(0);
    nodes.put("1MTA201848000007",info7);

    return nodes;
  }

  /**
   * 保存统计数据到缓存
   * @param statInfo
   */
  @Override
  public List<SCLineStatInfo> saveSCStatistics(Integer poiId,Date beginTime,Date endTime){
    String category="iot_sc_statistics";
    String key= poiId.toString();
    StoreKey storeKey=new StoreKey(category,key);
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    String field=format.format(beginTime);
    List<SCLineStatInfo> result=getStatisData(poiId,beginTime,endTime);
    redisStoreClient.hset(storeKey,field,result);
    return result;
  }

  /**
   * 返回每个监控设备的监控率
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  public List<SCStatisticInfo> getSCStatisticInfo(Integer poiId, Date beginTime,Date endTime){
    List<SCStatisticInfo> result=new LinkedList<>();
    String deviceList="'1MTA201848000001','MTA201848000002','1MTA201848000003','1MTA201848000004','1MTA201848000005'";
    List<String> tagList = scDataFilteDao.queryDistinctHfTagSn(poiId, beginTime, endTime);
    Map<String,SCStatisticInfo> map=getAllNode(poiId);
    //上链载具总数
    Integer total=0;
    for(String hfTagSn:tagList) {
      List<SCFilterData> dataList=scDataFilteDao.queryByHfTagSn(poiId,hfTagSn,beginTime,endTime);
      int j=-1; //判断是否遍历到上链口的监控设备监控到的数据
      int i=-1;
       for(SCFilterData data:dataList){
         i++;
        if(scDeviceService.isFirstNode(data.getSn(),poiId)){
          j=i;
          //上链口误扫描到的数据
          if(i==dataList.size()-1){
            continue;
          }else if(scDeviceService.isFirstNode(dataList.get(i+1).getSn(),poiId)){
            continue;
          }
          SCStatisticInfo info=map.get(data.getSn());
          info.setDetectionSize(info.getDetectionSize()+1);
          info.setTotalSize(info.getTotalSize()+1);
          total++;
         }
         else{
           //如果是下链口设备，设置j为-1
          if(data.getSn().equals("1MTA201848000007")&&j!=-1){
            j=-1;
            SCStatisticInfo info=map.get(data.getSn());
            info.setDetectionSize(info.getDetectionSize()+1);
          }
          //下链口之后的数据，如果不是上链口监控的数据则不计算
          if(j!=-1){
            SCStatisticInfo info=map.get(data.getSn());
            info.setDetectionSize(info.getDetectionSize()+1);
          }
        }
      }

    }
    //设置载具总数
    for(Map.Entry<String,SCStatisticInfo> enty:map.entrySet()){
      if(!scDeviceService.isFirstNode(enty.getValue().getSn(),poiId)){
        enty.getValue().setTotalSize(total);
      }
    }

    result.add(map.get("1MTA201848000001"));
    result.add(map.get("1MTA201848000002"));
    result.add(map.get("1MTA201848000003"));
    result.add(map.get("1MTA201848000004"));
    result.add(map.get("1MTA201848000005"));
    result.add(map.get("1MTA201848000010"));
    result.add(map.get("1MTA201848000006"));
    result.add(map.get("1MTA201848000007"));

    return result;
  }

  /**
   * 保存每个监控设备的监控率到缓存
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  public List<SCStatisticInfo> saveSCStatisticInfo(Integer poiId, Date beginTime,Date endTime){
    String category="iot_sc_statistics";
    String key= poiId.toString()+"rate";
    StoreKey storeKey=new StoreKey(category,key);
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    String field=format.format(beginTime);
    List<SCStatisticInfo> result=getSCStatisticInfo(poiId,beginTime,endTime);
    redisStoreClient.hset(storeKey,field,result);
    return result;
  }

  /**
   * 返回每个监控设备的监控率
   * @param poiId
   * @param beginTime
   * @param endTime
   * @return
   */
  @Override
  public List<SCStatisticInfo> getSCDeviceStatistcis(Integer poiId,Date beginTime,Date endTime) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String field = format.format(beginTime);
    List<List<SCStatisticInfo>> caches = null;
    //如果是今天之前的数据，从缓存读取
    if (!field.equals(format.format(new Date()))) {
      String category = "iot_sc_statistics";
      String key = poiId.toString() + "rate";
      StoreKey storeKey = new StoreKey(category, key);
      caches = redisStoreClient.hmget(storeKey, field);
      if (caches.get(0) == null) {
        logger.error("poiId:" + poiId + ";" + field + "无法获取缓存的监控成功率统计数据");
      } else {
        return caches.get(0);
      }
    }
    return getSCStatisticInfo(poiId, beginTime, endTime);
  }


}
