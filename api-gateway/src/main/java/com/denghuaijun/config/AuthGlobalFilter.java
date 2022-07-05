package com.denghuaijun.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * AuthGlobalFilter
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/5 11:12
 * @Description 自定义认证授权全局过滤器,去校验所有请求参数是否包含token
 * http://localhost:7000/product-service/product/1?age=30&token=123
 */
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    //判断逻辑
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("token");
        if (StringUtils.isBlank(token)){
            System.out.println("鉴权失败。。。");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        //若鉴权成功，则继续执行后续流程
        return chain.filter(exchange);
    }
    //执行顺序，数值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
