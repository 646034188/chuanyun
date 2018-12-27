package com.sankuai.mall.iot.chuanyunapi.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil
{
  //将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
  public static Date strintToDate(String date){
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ParsePosition pos = new ParsePosition(0);

    return formatter.parse(date,pos);
  }

  //获取两个时间直接相差的秒数
  public static long getBetween(Date first,Date second){
    long a=first.getTime();
    long b=second.getTime();
    return (a-b)/1000L;

  }
}
