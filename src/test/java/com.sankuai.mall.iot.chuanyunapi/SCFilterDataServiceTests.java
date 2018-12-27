package com.sankuai.mall.iot.chuanyunapi;

import com.alibaba.fastjson.JSONArray;
import com.sankuai.mall.iot.chuanyunapi.dto.SCLineStatInfo;
import com.sankuai.mall.iot.chuanyunapi.service.SCDataFilterService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
 import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SCFilterDataServiceTests{

  @Autowired
  private SCDataFilterService scDataFilterService;

  @Test
  public void query() throws ParseException {
    Integer poiId=18;
    String firstTime="2018-12-10 08:00:00";
    String endTime="2018-12-10 23:00:00";
    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    List<SCLineStatInfo> result=scDataFilterService.getStatisData(poiId,dateFormat.parse(firstTime),dateFormat.parse(endTime));
    System.out.println(JSONArray.toJSON(result));
  }
}
