package com.denghuaijun.config;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * AgeRoutePredicateFactory
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 18:39
 * @Description 自定义一个断言工厂类 实现只有18-60岁的人可以访问
 */
@Component
public class AgeRoutePredicateFactory extends AbstractRoutePredicateFactory<AgeRoutePredicateFactory.Config> {

    public AgeRoutePredicateFactory(Class<Config> configClass) {
        super(configClass);
    }

    public AgeRoutePredicateFactory() {
        super(AgeRoutePredicateFactory.Config.class);
    }

    @Override
    public Predicate<ServerWebExchange> apply(AgeRoutePredicateFactory.Config config) {
        return new Predicate<ServerWebExchange>() {
            @Override
            public boolean test(ServerWebExchange serverWebExchange) {
                //从serverWebExchange获取传入的参数
                String ageStr = serverWebExchange.getRequest().getQueryParams().getFirst("age");
                if (StringUtils.isNotEmpty(ageStr)){
                    int age = Integer.parseInt(ageStr);
                    return age > config.getMinAge() && age < config.getMaxAge();
                }
                return true;
            }
        };
    }
    //用于从配置文件中回去参数值赋值到配置类中的属性
    @Override
    public List<String> shortcutFieldOrder() {
        //这里属性的顺序要跟配置文件中参数对应值的顺序一致
        return Arrays.asList("minAge","maxAge");
    }
    //自定义配置类用于接受配置文件的参数
    @Data
    class Config{

        private int minAge;
        private int maxAge;

    }

    public static void main(String[] args) {
        long l = 1655133856000L;
        System.out.println(LocalDateTime.now());
    }
}


