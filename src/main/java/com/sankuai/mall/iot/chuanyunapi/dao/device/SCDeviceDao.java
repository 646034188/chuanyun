package com.sankuai.mall.iot.chuanyunapi.dao.device;

import com.sankuai.mall.iot.chuanyunapi.dto.Device;
import java.util.List;
import org.apache.ibatis.annotations.*;

public interface SCDeviceDao {
  @Select("SELECT device_id,create_time from iot_device_info where poi_id=#{poiId} and type=#{type} "
       + "ORDER BY CREATE_TIME DESC LIMIT #{row},#{numberPerPage}")
  @Results({
      @Result(property = "deviceId", column = "device_id"),
       @Result(property = "createTime", column = "create_time")
  })
  List<Device> getDeviceList(@Param("poiId") Integer poiId,@Param("type")Integer type,@Param("row")Integer row,
  @Param("numberPerPage")Integer numberPerPage);

  @Select("SELECT COUNT(*) FROM iot_device_info WHERE POI_ID =#{poiId} and type=#{type}")
  Integer getCout(@Param("poiId")Integer poiId,@Param("type")Integer type);

}
