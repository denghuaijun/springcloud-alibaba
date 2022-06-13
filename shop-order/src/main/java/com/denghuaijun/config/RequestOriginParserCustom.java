package com.denghuaijun.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * RequestOriginParserCustom
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/10 14:26
 * @Description sentinal接口来源实现类,用户授权规则配置应用名
 */
@Slf4j
@Component
public class RequestOriginParserCustom implements RequestOriginParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestOriginParserCustom.class);

    @Override
    public String parseOrigin(HttpServletRequest request) {
        String serviceName = request.getParameter("serviceName");
        LOGGER.info("获取请求中的来源服务名：{}",serviceName);
        return serviceName;
    }
}
