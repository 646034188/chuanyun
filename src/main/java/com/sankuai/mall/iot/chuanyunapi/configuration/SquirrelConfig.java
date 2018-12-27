package com.sankuai.mall.iot.chuanyunapi.configuration;

import com.dianping.squirrel.client.impl.redis.RedisClientBuilder;
import com.dianping.squirrel.client.impl.redis.RedisStoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yanglin on 2018-11-14.
 */
@Configuration
public class SquirrelConfig {
    /**
     * cat 上报filter
     *
     * @return
     */
    @Bean
    public RedisStoreClient getSquirrelClient(@Value("${squirrel.clusterName}") String clusterName) {
        RedisStoreClient redisClient = new RedisClientBuilder(clusterName)
                .readTimeout(1000)             // 超时时间
                .routerType("master-slave")     // 路由策略
                .build();                      // 创建客户端 
        return redisClient;
    }

}
