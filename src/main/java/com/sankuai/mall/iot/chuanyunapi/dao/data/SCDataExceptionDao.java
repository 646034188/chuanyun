package com.sankuai.mall.iot.chuanyunapi.dao.data;

import com.sankuai.mall.iot.chuanyunapi.po.SCExceptionData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface SCDataExceptionDao {
  /**
   * 插入数据
   */
  @Insert("INSERT INTO iot_data_exception (data_id,sn,hf_tag_sn,first_capture_time,exception_type,exception_type_name)"+
  " value (#{dataId},#{sn},#{hfTagSn},#{firstCaptureTime},#{exceptionType},#{exceptionTypeName})")
  @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
  void insert(SCExceptionData data);

  @Select("SELECT COUNT(*) FROM iot_data_exception WHERE data_id=#{dataId}")
  Integer getCount(Integer dataId);

  @Update("UPDATE iot_data_exception SET exception_type=#{exceptionType} ,exception_type_name=#{exceptionTypeName} WHERE data_id=#{dataId}")
  void update(SCExceptionData data);
}
