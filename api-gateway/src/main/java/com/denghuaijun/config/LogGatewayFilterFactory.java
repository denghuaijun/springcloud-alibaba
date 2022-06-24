package com.denghuaijun.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

/**
 * LogGateWayFilterFactory
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/23 17:56
 * @Description 自定义局部过滤器
 */
@Component
public class LogGatewayFilterFactory extends AbstractGatewayFilterFactory<LogGatewayFilterFactory.Config> {

    public LogGatewayFilterFactory() {
        super(LogGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("consoleLog","cacheLog");
    }

    //过滤器逻辑
    @Override
    public GatewayFilter apply(LogGatewayFilterFactory.Config config) {
        return new GatewayFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                if (config.isCacheLog()){
                    System.out.println("cacheLog已经开启。。。。");
                }
                if (config.isConsoleLog()){
                    System.out.println("consoleLog已经开启。。。。");
                }
                return chain.filter(exchange);
            }
        };
    }

    //配置类，接受配置参数
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config{
        private boolean consoleLog;
        private boolean cacheLog;
    }
}
