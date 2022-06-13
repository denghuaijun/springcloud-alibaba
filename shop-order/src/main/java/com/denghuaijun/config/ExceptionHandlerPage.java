package com.denghuaijun.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.UrlBlockHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ExceptionHandlerPage
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/6/10 15:27
 * @Description 自定义异常返回页面
 */
@Component
public class ExceptionHandlerPage implements UrlBlockHandler {

    //BlockException 异常接口，包括sentinel的五个异常
    @SentinelResource //用于定义资源，并提供可选的异常处理和fallback配置项，其主要参数如下：
    //FlowException 限流异常
    //DegradeException 降级异常
    //ParamFlowException 热点参数限流异常
    //AuthorityException 授权异常
    //SystemBlockException 系统负载异常
    @Override
    public void blocked(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        ResponseData data = null;
        if (e instanceof FlowException){
            data = new ResponseData(-1,"接口被限流了...");
        }else if (e instanceof DegradeException){
            data = new ResponseData(-2,"接口被降级了...");
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(data));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class ResponseData{
        private int code;
        private String message;

    }
}
