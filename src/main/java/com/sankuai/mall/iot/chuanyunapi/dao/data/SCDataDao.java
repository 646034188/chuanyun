package com.sankuai.mall.iot.chuanyunapi.dao.data;

 import com.sankuai.mall.iot.chuanyunapi.enty.SCData;
import java.util.Date;
import java.util.List;
 import org.apache.ibatis.annotations.*;
 public interface SCDataDao {
   @Select("SELECT * FROM iot_sc_data WHERE HF_TAG_SN=#{hfTagSn} and CAPTURE_DEVICE_SN=#{sn} and DATA_TYPE=2 and"
       + " CAPTURE_TIME>#{captureTime} and  CAPTURE_TIME<=#{endTime} ORDER BY CAPTURE_TIME LIMIT 1")
   SCData getOut(@Param("hfTagSn") String hfTagSn,
       @Param("captureTime") Date captureTime, @Param("sn") String sn,
       @Param("endTime")Date endTime);

   @Select("SELECT COUNT(DISTINCT a.id) FROM iot_sc_data a,iot_sc_data b WHERE a.POI_ID =#{poiId} and a.CAPTURE_DEVICE_SN=#{sn}  and a.DATA_TYPE=2 and a.CAPTURE_TIME>=#{beginTime} and a.CAPTURE_TIME<=#{endTime}"
       + " and a.hf_tag_sn=b.hf_tag_sn and b.capture_device_sn=#{endSn} and b.CAPTURE_TIME>=#{beginTime} and b.CAPTURE_TIME<=#{endTime} and b.CAPTURE_TIME>a.CAPTURE_TIME "
       + "and b.CAPTURE_TIME>a.CAPTURE_TIME")
   Integer getEntranceCount(@Param("poiId")Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);


   @Select("SELECT COUNT(DISTINCT a.id) FROM iot_sc_data a,iot_sc_data b  WHERE a.POI_ID =#{poiId} and a.HF_TAG_SN IN (${hfTagSnList}) and a.CAPTURE_DEVICE_SN=#{sn} and a.DATA_TYPE=2 and a.CAPTURE_TIME>=#{beginTime}"
       + " and a.CAPTURE_TIME<=#{endTime} and a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and  b.capture_device_sn=#{endSn} and b.CAPTURE_TIME>=#{beginTime} and b.CAPTURE_TIME<=#{endTime} "
       + " and b.CAPTURE_TIME>a.CAPTURE_TIME")
   Integer getCount(@Param("poiId")Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
       @Param("hfTagSnList")String hfTagSnList,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);


   @Select("SELECT * FROM iot_sc_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.CAPTURE_DEVICE_SN="
       + "#{sn} and a.CAPTURE_TIME>=#{beginTime} and a.CAPTURE_TIME<=#{endTime} and "
       + " EXISTS (SELECT * FROM iot_sc_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and b.capture_device_sn=#{endSn} and b.CAPTURE_TIME>=#{beginTime} "
       + " and b.CAPTURE_TIME<=#{endTime} and b.CAPTURE_TIME>a.CAPTURE_TIME) "
       + " ORDER BY CAPTURE_TIME DESC LIMIT #{row},#{numberPerPage}")
   @Results({
       @Result(property = "id", column = "id"),
       @Result(property = "hfTagSn", column = "hf_tag_sn"),
       @Result(property = "captureTime", column = "capture_time"),

   })
   List<SCData> getEntranceList(@Param("poiId") Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime,
       @Param("row") Integer row,
       @Param("numberPerPage") Integer numberPerPage);



   @Select("SELECT DISTINCT HF_TAG_SN FROM iot_sc_data WHERE POI_ID =#{poiId} and DATA_TYPE=2 and CAPTURE_DEVICE_SN="
       + "#{sn} and CAPTURE_TIME>=#{beginTime} and CAPTURE_TIME<=#{endTime}")
   List<String> getEntrance(@Param("poiId") Integer poiId, @Param("sn") String sn,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);



   @Select("SELECT COUNT(DISTINCT a.id)  FROM iot_sc_data a,iot_sc_data b WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.CAPTURE_DEVICE_SN="
       + "#{sn} and a.CAPTURE_TIME>=#{beginTime} and a.CAPTURE_TIME<=#{endTime} and a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and  b.capture_device_sn=#{endSn} and b.CAPTURE_TIME>=#{beginTime} and b.CAPTURE_TIME<=#{endTime}"
       + " and b.CAPTURE_TIME>a.CAPTURE_TIME")
   Integer getCountOfEntrance(@Param("poiId") Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);






   @Select("SELECT * FROM iot_sc_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.CAPTURE_DEVICE_SN="
       + "#{sn} and a.HF_TAG_SN IN (${hfTagSnList}) and a.CAPTURE_TIME>=#{beginTime} and a.CAPTURE_TIME<=#{endTime} and EXISTS (SELECT * FROM iot_sc_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and  b.capture_device_sn=#{endSn} and b.CAPTURE_TIME>=#{beginTime} "
       + " and b.CAPTURE_TIME<=#{endTime} and b.CAPTURE_TIME>a.CAPTURE_TIME"
       + ") ORDER BY CAPTURE_TIME DESC LIMIT #{row},#{numberPerPage}")
   @Results({
       @Result(property = "id", column = "id"),
       @Result(property = "hfTagSn", column = "hf_tag_sn"),
       @Result(property = "captureTime", column = "capture_time"),

   })
   List<SCData> getInList(@Param("poiId") Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,@Param("hfTagSnList")String hfTagSnList,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime,
       @Param("row") Integer row,
       @Param("numberPerPage") Integer numberPerPage);


   @Select({
       "SELECT * FROM iot_sc_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.CAPTURE_DEVICE_SN IN "+
        "(${snList}) and a.HF_TAG_SN IN (${hfTagSnList}) and a.CAPTURE_TIME>=#{beginTime} and a.CAPTURE_TIME<=#{endTime} "+
       " and EXISTS (SELECT * FROM iot_sc_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and if(a.CAPTURE_DEVICE_SN=\'1MTA201848000010 \',  b.capture_device_sn='1MTA201848000006', b.capture_device_sn=\'1MTA201848000006\') and b.CAPTURE_TIME>=#{beginTime} "+
       " and b.CAPTURE_TIME<=#{endTime}) "+
        "ORDER BY a.CAPTURE_TIME DESC LIMIT #{row},#{numberPerPage}"
   })
   @Results({
       @Result(property = "id", column = "id"),
       @Result(property = "hfTagSn", column = "hf_tag_sn"),
       @Result(property = "captureTime", column = "capture_time"),

   })
   List<SCData> getList(@Param("poiId") Integer poiId, @Param("snList") String snList,@Param("hfTagSnList")String hfTagSnList,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime,
       @Param("row") Integer row,
       @Param("numberPerPage") Integer numberPerPage);



   @Select("SELECT DISTINCT HF_TAG_SN  FROM iot_sc_data WHERE POI_ID=#{poiId} AND CAPTURE_DEVICE_SN in (${deviceSnList}) and DATA_TYPE=#{dataType} and"
       + " CAPTURE_TIME>#{beginTime} and  CAPTURE_TIME<=#{endTime} ")
    List<String > getHfTagList(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("deviceSnList")String deviceSnList,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

    @Select("SELECT COUNT(DISTINCT HF_TAG_SN) FROM iot_sc_data WHERE poi_id=#{poiId} AND CAPTURE_DEVICE_SN=#{sn} and DATA_TYPE=#{dataType} and "+
        " CAPTURE_TIME>#{beginTime} and  CAPTURE_TIME<=#{endTime}"
     )
    Integer getSizeOfEntrance(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("sn")String sn,
        @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);


   @Select(
         "SELECT COUNT(DISTINCT HF_TAG_SN) FROM iot_sc_data WHERE poi_id=#{poiId} AND CAPTURE_DEVICE_SN=#{sn} and DATA_TYPE=#{dataType} and "+
         " CAPTURE_TIME>#{beginTime} and  CAPTURE_TIME<=#{endTime} and "+
         " hf_tag_sn in (${hfTagSnList})")
   Integer getSize(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("sn")String sn,@Param("hfTagSnList")String hfTagSnList,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);


   @Select(
       "SELECT COUNT(DISTINCT HF_TAG_SN) FROM iot_sc_data WHERE poi_id=#{poiId} and DATA_TYPE=#{dataType} and "+
           " CAPTURE_TIME>#{beginTime} and  CAPTURE_TIME<=#{endTime}")
   Integer getTotalSize(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,
       @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);


  }

