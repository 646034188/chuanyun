package com.sankuai.mall.iot.chuanyunapi.configuration;

import com.sankuai.inf.octo.mns.hlb.HttpServerPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${app.name}")
  private String appName;

  @Value("${server.port}")
  private int port;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**");
  }

  @Bean(initMethod = "publish", destroyMethod = "destroy")
  public HttpServerPublisher httpServerPublisher() {
    HttpServerPublisher httpServerPublisher = new HttpServerPublisher();
    httpServerPublisher.setAppKey(appName);
    httpServerPublisher.setPort(port);
    return httpServerPublisher;
  }
}



