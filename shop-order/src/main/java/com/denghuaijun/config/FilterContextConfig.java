package com.denghuaijun.config;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FilterContextConfig
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/10 12:03
 * @Description 自定义过滤器禁止收敛URL入口的context
 */
@Configuration
public class FilterContextConfig {

    @Bean
    public FilterRegistrationBean sentinelFilterRegistration(){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new CommonFilter());
        registration.addUrlPatterns("/*");
        //入口资源关闭
        registration.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY,"false");
        registration.setName("sentinelFilter");
        registration.setOrder(1);
        return registration;
    }
}
