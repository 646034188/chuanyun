package com.sankuai.mall.iot.chuanyunapi.dao.data;

import com.sankuai.mall.iot.chuanyunapi.dto.SCDataInfo;
import com.sankuai.mall.iot.chuanyunapi.enty.SCData;
import com.sankuai.mall.iot.chuanyunapi.enty.SCFilterData;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

public interface SCDataFilteDao {
  @Select(
      "SELECT *  FROM iot_sc_filter_data WHERE poi_id=#{poiId} AND SN=#{sn} and DATA_TYPE=#{dataType} and "+
          " last_capture_time>#{beginTime} and  last_capture_time<=#{endTime} order by hf_tag_sn,last_capture_time desc")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "sn", column = "sn"),
      @Result(property = "hfTagSn", column = "hf_tag_sn"),
      @Result(property = "firstCaptureTime", column = "first_capture_time"),
      @Result(property = "lastCaptureTime", column = "last_capture_time"),


  })
   List<SCFilterData> getSCDataList(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("sn")String sn,
      @Param("beginTime") Date beginTime,@Param("endTime")Date endTime);

  @Select(
      "SELECT *  FROM iot_sc_filter_data WHERE poi_id=#{poiId} AND SN=#{sn} and DATA_TYPE=#{dataType} and HF_TAG_SN=#{hfTagSn} and "+
          " FIRST_CAPTURE_TIME>#{beginTime} and  FIRST_CAPTURE_TIME<=#{endTime} order by FIRST_CAPTURE_TIME  limit 1")
  SCFilterData getSCData(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("sn")String sn,@Param("hfTagSn")String hfTagSn,
      @Param("beginTime") Date beginTime,@Param("endTime")Date endTime);

  /**
   * 获取同时通过起始点和终点的
   * @param poiId
   * @param sn
   * @param endSn
   * @param beginTime
   * @param endTime
   * @param row
   * @param numberPerPage
   * @return
   */
  @Select("SELECT id,sn,hf_tag_sn,last_capture_time FROM iot_sc_filter_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.sn="
      + "#{sn} and a.first_capture_time>=#{beginTime} and a.first_capture_time<=#{endTime} and "
      + " EXISTS (SELECT * FROM iot_sc_filter_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and b.sn=#{endSn} and b.first_capture_time>=#{beginTime} "
      + " and b.first_capture_time<=#{endTime} and b.first_capture_time>a.first_capture_time) "
      + " ORDER BY first_capture_time DESC LIMIT #{row},#{numberPerPage}")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "hfTagSn", column = "hf_tag_sn"),
      @Result(property = "sn", column = "sn"),

      @Result(property = "captureTime", column = "last_capture_time"),

  })
  List<SCData> getList(@Param("poiId") Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime,
      @Param("row") Integer row,
      @Param("numberPerPage") Integer numberPerPage);


  /**
   * 获取同时通过起始点和终点的
   * @param poiId
   * @param sn
   * @param endSn
   * @param beginTime
   * @param endTime
   * @param row
   * @param numberPerPage
   * @return
   */
  @Select("SELECT id,sn,hf_tag_sn,last_capture_time FROM iot_sc_filter_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.sn in"
      + "(${snList}) and a.hf_tag_sn in (${hfTagSnList})  and a.first_capture_time>=#{beginTime} and a.first_capture_time<=#{endTime} and "
      + " EXISTS (SELECT * FROM iot_sc_filter_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 "
      + " and b.first_capture_time<=#{endTime} and b.first_capture_time>a.first_capture_time) "
      + " ORDER BY first_capture_time DESC LIMIT #{row},#{numberPerPage}")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "hfTagSn", column = "hf_tag_sn"),
      @Result(property = "sn", column = "sn"),

      @Result(property = "captureTime", column = "last_capture_time"),

  })
  List<SCData> getTotalList(@Param("poiId") Integer poiId, @Param("snList") String snList,@Param("hfTagSnList")String hfTagSnList,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime,
      @Param("row") Integer row,
      @Param("numberPerPage") Integer numberPerPage);

  /**
   * 获取监控同时通过上链口监控点和终点的设备的数量
   * @param poiId
   * @param sn
   * @param endSn
   * @param beginTime
   * @param endTime
   * @return
   */
  @Select("SELECT COUNT(*) FROM iot_sc_filter_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.sn="
      + "#{sn} and a.first_capture_time>=#{beginTime} and a.first_capture_time<=#{endTime} and "
      + " EXISTS (SELECT * FROM iot_sc_filter_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and b.sn=#{endSn} and b.first_capture_time>=#{beginTime} "
      + " and b.first_capture_time<=#{endTime} and b.first_capture_time>a.first_capture_time)")
  Integer getCountOfEntrance(@Param("poiId")Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

  /**
   * 入口处监控到的载具通过出口时的信息
   * @param hfTagSn
   * @param captureTime
   * @param sn
   * @param endTime
   * @return
   */
  @Select("SELECT first_capture_time FROM iot_sc_filter_data WHERE HF_TAG_SN=#{hfTagSn} and sn=#{sn} and DATA_TYPE=2 and"
      + " first_capture_time>#{captureTime} and  first_capture_time<=#{endTime} ORDER BY first_capture_time LIMIT 1")
  Date getOut(@Param("hfTagSn") String hfTagSn,
      @Param("captureTime") Date captureTime, @Param("sn") String sn,
      @Param("endTime")Date endTime);
  /**
   *获取上链口扫描到的设备
   */
  @Select("SELECT DISTINCT HF_TAG_SN FROM iot_sc_filter_data WHERE POI_ID =#{poiId} and DATA_TYPE=2 and SN="
      + "#{sn} and first_capture_time>=#{beginTime} and first_capture_time<=#{endTime}")
  List<String> getEntrance(@Param("poiId") Integer poiId, @Param("sn") String sn,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

  /**
   * 获取同时经过起始点监控设备和终点监控设备的载具数量
   * @param poiId
   * @param sn
   * @param endSn
   * @param hfTagSnList
   * @param beginTime
   * @param endTime
   * @return
   */
  @Select("SELECT COUNT(a.id) FROM iot_sc_filter_data a WHERE a.POI_ID =#{poiId} and a.HF_TAG_SN IN (${hfTagSnList}) and a.SN=#{sn} and a.DATA_TYPE=2 and a.first_capture_time>=#{beginTime} and a.first_capture_time<=#{endTime} "
      + " and EXISTS (SELECT * from iot_sc_filter_data b where a.first_capture_time<=#{endTime} and a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and  b.sn=#{endSn} and b.first_capture_time>=#{beginTime} and b.first_capture_time<=#{endTime} "
      + " and b.first_capture_time>a.first_capture_time)")
  Integer getCount(@Param("poiId")Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,
      @Param("hfTagSnList")String hfTagSnList,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

  /**
   * 获取
   * @param poiId
   * @param sn
   * @param endSn
   * @param hfTagSnList
   * @param beginTime
   * @param endTime
   * @param row
   * @param numberPerPage
   * @return
   */
  @Select("SELECT id,hf_tag_sn,sn,last_capture_time FROM iot_sc_filter_data a WHERE a.POI_ID =#{poiId} and a.DATA_TYPE=2 and a.SN="
      + "#{sn} and a.HF_TAG_SN IN (${hfTagSnList}) and a.first_capture_time>=#{beginTime} and a.first_capture_time<=#{endTime} and EXISTS (SELECT * FROM iot_sc_filter_data b WHERE a.hf_tag_sn=b.hf_tag_sn and b.POI_ID =#{poiId} and b.DATA_TYPE=2 and  b.SN=#{endSn} and b.first_capture_time>=#{beginTime} "
      + " and b.first_capture_time<=#{endTime} and b.first_capture_time>a.first_capture_time"
      + ") ORDER BY first_capture_time DESC LIMIT #{row},#{numberPerPage}")
  @Results({
      @Result(property = "id", column = "id"),
      @Result(property = "hfTagSn", column = "hf_tag_sn"),
      @Result(property = "sn", column = "sn"),
      @Result(property = "captureTime", column = "last_capture_time"),
  })
  List<SCData> getInList(@Param("poiId") Integer poiId, @Param("sn") String sn,@Param("endSn")String endSn,@Param("hfTagSnList")String hfTagSnList,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime,
      @Param("row") Integer row,
      @Param("numberPerPage") Integer numberPerPage);

  @Select("SELECT DISTINCT(hf_tag_sn) FROM iot_sc_filter_data where poi_id=#{poiId} and data_type=2 " +
      "and first_capture_time>=#{startTime} and first_capture_time<=#{endTime} ")
  List<String> queryDistinctHfTagSn(@Param("poiId") int poiId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

  @Select("SELECT * FROM iot_sc_filter_data where poi_id=#{poiId} and data_type=2 and hf_tag_sn = #{hfTagSn} " +
      "and first_capture_time>=#{startTime} and first_capture_time<=#{endTime} order by id asc ")
  List<SCFilterData> queryByHfTagSn(@Param("poiId") int poiId, @Param("hfTagSn") String hfTagSn, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

  /**
   * 获取所有上链口监控到的设备
   * @param poiId
   * @param dataType
   * @param deviceSnList
   * @param beginTime
   * @param endTime
   * @return
   */
  @Select("SELECT DISTINCT HF_TAG_SN  FROM iot_sc_filter_data WHERE POI_ID=#{poiId} AND SN in (${deviceSnList}) and DATA_TYPE=#{dataType} and"
      + " first_capture_time>#{beginTime} and  first_capture_time<=#{endTime} ")
  List<String > getHfTagList(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("deviceSnList")String deviceSnList,@Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

  @Select("SELECT COUNT(*) FROM iot_sc_filter_data WHERE poi_id=#{poiId} AND SN=#{sn} and DATA_TYPE=#{dataType} and "+
      " first_capture_time>#{beginTime} and  first_capture_time<=#{endTime}"
  )
  Integer getSizeOfEntrance(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("sn")String sn,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);

  @Select(
      "SELECT COUNT(*) FROM iot_sc_filter_data WHERE poi_id=#{poiId} AND SN=#{sn} and DATA_TYPE=#{dataType} and "+
          " first_capture_time>#{beginTime} and  first_capture_time<=#{endTime} and "+
          " hf_tag_sn in (${hfTagSnList})")
  Integer getSize(@Param("poiId") Integer poiId,@Param("dataType")Integer dataType,@Param("sn")String sn,@Param("hfTagSnList")String hfTagSnList,
      @Param("beginTime")Date beginTime,@Param("endTime")Date endTime);


}
