package com.sankuai.mall.iot.chuanyunapi.business;

import com.dianping.squirrel.client.StoreKey;
import com.dianping.squirrel.client.impl.redis.RedisStoreClient;
import com.sankuai.mall.iot.chuanyunapi.service.SCDataFilterService;
 import java.util.Calendar;
import java.util.Date;
  import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by yanglin on 2018-12-12
 */
@Component
public class ScheduledTask {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private SCDataFilterService sCDataFilterService;

    @Autowired
    private RedisStoreClient redisStoreClient;

    @Scheduled(cron = "0 0 4 * * *")
    public void scheduled() {
        String category = "iot-base-data";
        String key = "distributelock_ScheduledTask";
        StoreKey storeKey = new StoreKey(category, key);
        /**
         * 使用分布式锁保证多台服务器只有一台机器执行定时任务
         */
        if (redisStoreClient.setnx(storeKey, String.valueOf(System.currentTimeMillis()))) {
            redisStoreClient.expire(storeKey, 5);
            logger.info("start statistics task!");
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date endTime = calendar.getTime();
                calendar.add(Calendar.DATE, -1);
                Date startTime = calendar.getTime();
                sCDataFilterService.saveSCStatistics(18, startTime, endTime);
            } catch (Exception e) {
                logger.error("statistics task occur error", e);
            }
        }
    }
    /**
     *监测成功率定时任务
     */
    @Scheduled(cron = "0 30 4 * * *")
    public void rateScheduled() {
        String category = "iot-base-data";
        String key = "distributelock_ScheduledTask";
        StoreKey storeKey = new StoreKey(category, key);
        /**
         * 使用分布式锁保证多台服务器只有一台机器执行定时任务
         */
        if (redisStoreClient.setnx(storeKey, String.valueOf(System.currentTimeMillis()))) {
            redisStoreClient.expire(storeKey, 5);
            logger.info("start rate statistics task!");
            try {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date endTime = calendar.getTime();
                calendar.add(Calendar.DATE, -1);
                Date startTime = calendar.getTime();
                sCDataFilterService.saveSCStatisticInfo(18, startTime, endTime);
            } catch (Exception e) {
                logger.error("rate statistics task occur error", e);
            }
        }
    }

}
