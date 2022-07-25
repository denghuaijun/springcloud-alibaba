package com.denghuaijun.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * DataSourceProxyConfig
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/25 15:06
 * @Description seata是通过代理数据源实现事务分布的，
 * 所以需要配置DataSourceProxy的Bean。且是@primary默认的数据源，
 * 否则事务不会回滚，无法实现分布式事务
 */
@Configuration
public class DataSourceProxyConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DruidDataSource druidDataSource(){
        return new DruidDataSource();
    }

    @Primary
    @Bean
    public DataSourceProxy dataSource(DruidDataSource druidDataSource){
        return new DataSourceProxy(druidDataSource);
    }
}
