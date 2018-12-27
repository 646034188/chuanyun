package com.sankuai.mall.iot.chuanyunapi.configuration;

import com.dianping.zebra.dao.datasource.ZebraRoutingDataSource;
import com.dianping.zebra.dao.mybatis.ZebraMapperScannerConfigurer;
import com.dianping.zebra.group.jdbc.GroupDataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by yanglin on 2018-11-22.
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
public class DataSourceConfig {

    @Bean(name="iotDataDataSource")
    public GroupDataSource iotDataDataSource(@Value("${jdbc.iot_data.ref}") String jdbcRef,
                                      @Value("${jdbc.iot_data.lazyinit}") Boolean lazyInit) {
        GroupDataSource groupDataSource = new GroupDataSource();
        groupDataSource.setJdbcRef(jdbcRef);
        groupDataSource.setPoolType("tomcat-jdbc");
        groupDataSource.setMinPoolSize(5);
        groupDataSource.setMaxPoolSize(20);
        groupDataSource.setInitialPoolSize(5);
        groupDataSource.setCheckoutTimeout(1000);
        groupDataSource.setIdcAware(true);
        groupDataSource.setConnectionInitSql("set names utf8mb4");
        groupDataSource.setLazyInit(lazyInit);
        groupDataSource.init();
        return groupDataSource;
    }

    @Bean(name="iotDeviceDataSource")
    public GroupDataSource iotDeviceDataSource(@Value("${jdbc.iot_device.ref}") String jdbcRef,
        @Value("${jdbc.iot_device.lazyinit}") Boolean lazyInit) {
        GroupDataSource groupDataSource = new GroupDataSource();
        groupDataSource.setJdbcRef(jdbcRef);
        groupDataSource.setPoolType("tomcat-jdbc");
        groupDataSource.setMinPoolSize(5);
        groupDataSource.setMaxPoolSize(20);
        groupDataSource.setInitialPoolSize(5);
        groupDataSource.setCheckoutTimeout(1000);
        groupDataSource.setIdcAware(true);
        groupDataSource.setConnectionInitSql("set names utf8mb4");
        groupDataSource.setLazyInit(lazyInit);
        groupDataSource.init();
        return groupDataSource;
    }

    @Bean(name = "routingDataSource")
    public ZebraRoutingDataSource zebraRoutingDataSource(@Qualifier("iotDataDataSource") GroupDataSource iotDataDataSource,@Qualifier("iotDeviceDataSource") GroupDataSource iotDeviceDataSource){
        ZebraRoutingDataSource zebraRoutingDataSource=new ZebraRoutingDataSource();
        Map<Object,Object> targetDataSourceMap=new HashMap();
        targetDataSourceMap.put("iotDataDataSource",iotDataDataSource);
        targetDataSourceMap.put("iotDeviceDataSource",iotDeviceDataSource);
        zebraRoutingDataSource.setTargetDataSources(targetDataSourceMap);
        Map<String,String> packageDataSourceKeyMap=new HashMap();
        packageDataSourceKeyMap.put("com.sankuai.mall.iot.chuanyunapi.dao.data","iotDataDataSource");
        packageDataSourceKeyMap.put("com.sankuai.mall.iot.chuanyunapi.dao.device","iotDeviceDataSource");
        zebraRoutingDataSource.setPackageDataSourceKeyMap(packageDataSourceKeyMap);

        return zebraRoutingDataSource;
    }

    @Bean(name = "zebraSqlSessionFactory")
    public SqlSessionFactoryBean zebraSqlSessionFactory(@Qualifier("routingDataSource") ZebraRoutingDataSource dataSource) throws IOException {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setTypeAliasesPackage("com.sankuai.mall.iot.chuanyunapi.dao");
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatis/mybatis-config.xml"));
//        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        sqlSessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*Mapper.xml"));
        return sqlSessionFactory;
    }

    @Bean
    public ZebraMapperScannerConfigurer zebraMapperScannerConfigurer() {
        ZebraMapperScannerConfigurer zebraMapperScannerConfigurer = new ZebraMapperScannerConfigurer();
        zebraMapperScannerConfigurer.setBasePackage("com.sankuai.mall.iot.chuanyunapi.dao");
        zebraMapperScannerConfigurer.setSqlSessionFactoryBeanName("zebraSqlSessionFactory");
        return zebraMapperScannerConfigurer;
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("routingDataSource") ZebraRoutingDataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
