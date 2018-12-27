package com.sankuai.mall.iot.chuanyunapi.sso;

 import com.sankuai.it.sso.sdk.spring.FilterFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.web.filter.DelegatingFilterProxy;
 import static javax.servlet.DispatcherType.REQUEST;
 @Configuration
 public class FilterConfiguration {
    @Value("${ssoSecret}")
    private String ssoSecret;

    @Value("${clientId}")
    private String clientId;

    @Bean
    public FilterRegistrationBean mtFilter() {
        DelegatingFilterProxy filter = new DelegatingFilterProxy();
        FilterRegistrationBean registration = new FilterRegistrationBean();
        filter.setTargetBeanName("mtFilterBean");
        filter.setTargetFilterLifecycle(true);

        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setDispatcherTypes(REQUEST);
        registration.setName("mtFilter");
        registration.setOrder(1);
//        如果有出现容器中 mtFilter 被注册两遍，需要配置  registration.setEnabled(false);
        registration.setEnabled(false);
        return registration;
    }


    @Bean
    public FilterFactoryBean mtFilterBean() {
        FilterFactoryBean filterFactoryBean = new FilterFactoryBean();
        filterFactoryBean.setClientId(clientId);
        filterFactoryBean.setSecret(ssoSecret);

        filterFactoryBean.setExcludedUriList("/static/**,/octo/checkAlive");
        filterFactoryBean.setIncludedUriList("/test/**,/login/**,/web/uploadWeb,/");
      //  filterFactoryBean.setAccessEnv("ppe");
       filterFactoryBean.setAccessEnv("prod");
//        filterFactoryBean.setSsoListener("com.sankuai.it.iam.dataentrance.listener.MySSOListener");
//        filterFactoryBean.setRewriteLocation("/123");
        filterFactoryBean.setLogoutUri("/logout");
        return filterFactoryBean;
    }
}
