package com.sankuai.mall.iot.chuanyunapi.configuration;

 import com.sankuai.it.sso.sdk.spring.FilterFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
 import org.springframework.web.filter.DelegatingFilterProxy;
  import static javax.servlet.DispatcherType.REQUEST;

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
    // 如果有出现容器中 mtFilter 被注册两遍，需要配置  registration.setEnabled(false);
    //registration.setEnabled(false);
    return registration;
  }

  /**
   * mtFilter配置
   可在本地配置具体的值，每一配置的含义参考第三节
   */
  @Bean
  public FilterFactoryBean mtFilterBean() {
    FilterFactoryBean filterFactoryBean = new FilterFactoryBean();
    filterFactoryBean.setClientId(clientId);
    filterFactoryBean.setSecret(ssoSecret);

    /**
     <!-- 需要 SSO 检查的 Url 配置, 多个以逗号分隔，允许换行
     单独配 includedUriList，includedUriList 以外的链接都不检查sso登录
     单独配 excludedUriList ， excludedUriList 以外的链接都会检查sso登录
     includedUriList，excludedUriList 都要有的时候，includedUriList 优先级更高  -->
     **/
    filterFactoryBean.setExcludedUriList("/static/**,/octo/checkAlive");

    //----------------------------------以下配置请业务方系统阅读其使用方法再行接入---------------------------------------------
    //<!--表示需要经过SSO过滤的uri，多个uri以','分割。两者都配置的情况下，includedUriList优先级更高 -->
    filterFactoryBean.setIncludedUriList("/test/**,/test1/**");

    /**
     <!--
     根据实际情况指定接入SSO线下(取值ppe)或线上(取值prod)环境。##请不要接入SSO的dev、test和staging环境，不提供稳定服务##
     默认不需要配置，SDK将根据客户端环境自动对齐，即dev、test、ppe对齐到SSO线下环境，staging和prod对齐到SSO线上环境
     -->
     */
    filterFactoryBean.setAccessEnv("ppe");//ppe或prod

    /**
     <!--
     根据实际情况指定该监听器，用于业务方需要进行额外的一些扩展功能。关于监听器，请参考：第五节
     -->
     */
    filterFactoryBean.setSsoListener("com.sankuai.it.iam.dataentrance.listener.MySSOListener");// or MySSOListener.class.getName()

    /**
     <!--
     根据实际情况指定，如果nginx配置Location时进行了rewrite抹除，请在这里填写该location，SDK2.0会在跳转时拼接回正确的url。否则不用填写。
     例如：/locationA/uriB经nginx转发后，到后端被重写成/uriB，则/locationA就是rewriteLocation，参考nginx配置：
     location /locationA/ {
     rewrite /locationA/?(.*)$ /$1 break;
     }
     -->
     */
    filterFactoryBean.setRewriteLocation("/123");

    //<!--可不配置，默认预留/sso/logout作为登出地址，业务方可以直接使用该uri-->
    filterFactoryBean.setLogoutUri("/logout");
    /**
     <!--可不配置，表示本应用使用的http或https协议，默认从header "X-Forwarded-Proto"中取值，除非用户自己指定-->
     */
    filterFactoryBean.setSchema("https");
    return filterFactoryBean;
  }
}