package com.denghuaijun.config;

import com.alibaba.csp.sentinel.command.handler.ModifyParamFlowRulesCommandHandler;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.FileRefreshableDataSource;
import com.alibaba.csp.sentinel.datasource.FileWritableDataSource;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * FilePersistence
 *首先Sentinel 控制台通过API将规则推送到客户端并更新到内存中，接着注册的写数据源会将新的规则保存到本地文件中
 * @author denghuaijun@eversec.cn
 * @date 2022/6/13 10:51
 * @Description 规则持久化到本地文件处理类
 */
public class FilePersistence implements InitFunc {

    @Value("${spring.application.name}")
    private String appName;


    @Override
    public void init() throws Exception {
        String ruleDir = System.getProperty("user.home")+"/sentinel-rules/"+appName;
        String flowRulePath = ruleDir + "/flow-rule.json"; //限流规则文件
        String degradeRulePath = ruleDir + "/degrade-rule.json"; //降级规则文件
        String systemRulePath = ruleDir +"/system-rule.json";//系统规则文件
        String authorityRulePath = ruleDir + "/authority-rule.json";//授权规则文件
        String paramFlowRulePath = ruleDir+"/param-flow-rule.json";//热点参数规则
        this.mkdirIfNotExists(ruleDir);
        this.createFileIfNotExists(flowRulePath);
        this.createFileIfNotExists(degradeRulePath);
        this.createFileIfNotExists(systemRulePath);
        this.createFileIfNotExists(authorityRulePath);
        this.createFileIfNotExists(paramFlowRulePath);

        //流控规则
        ReadableDataSource<String, List<FlowRule>> flowRuleRDS = new FileRefreshableDataSource<List<FlowRule>>(flowRulePath,flowRuleListParser);
        FlowRuleManager.register2Property(flowRuleRDS.getProperty());
        WritableDataSource<List<FlowRule>> flowRuleWds = new FileWritableDataSource<List<FlowRule>>(flowRulePath,this::enCodeJson);
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWds);
        //降级规则
        ReadableDataSource<String, List<DegradeRule>> degradeRuleRDS = new FileRefreshableDataSource<List<DegradeRule>>(degradeRulePath,degradeRuleListParser);
        DegradeRuleManager.register2Property(degradeRuleRDS.getProperty());
        WritableDataSource<List<DegradeRule>> degradeRuleWds = new FileWritableDataSource<List<DegradeRule>>(degradeRulePath,this::enCodeJson);
        WritableDataSourceRegistry.registerDegradeDataSource(degradeRuleWds);
        //系统规则
        ReadableDataSource<String, List<SystemRule>> systemRuleRDS = new FileRefreshableDataSource<List<SystemRule>>(systemRulePath,systemRuleListParser);
        SystemRuleManager.register2Property(systemRuleRDS.getProperty());
        WritableDataSource<List<SystemRule>> systemRuleWds = new FileWritableDataSource<List<SystemRule>>(systemRulePath,this::enCodeJson);
        WritableDataSourceRegistry.registerSystemDataSource(systemRuleWds);
        //授权规则
        ReadableDataSource<String, List<AuthorityRule>> authorityRuleRDS = new FileRefreshableDataSource<List<AuthorityRule>>(authorityRulePath,authorityRuleListParser);
        AuthorityRuleManager.register2Property(authorityRuleRDS.getProperty());
        WritableDataSource<List<AuthorityRule>> authorityRuleWds = new FileWritableDataSource<List<AuthorityRule>>(authorityRulePath,this::enCodeJson);
        WritableDataSourceRegistry.registerAuthorityDataSource(authorityRuleWds);
        //热点参数规则
        ReadableDataSource<String, List<ParamFlowRule>> paramFlowRuleRDS = new FileRefreshableDataSource<List<ParamFlowRule>>(paramFlowRulePath,paramFlowRuleListParser);
        ParamFlowRuleManager.register2Property(paramFlowRuleRDS.getProperty());
        WritableDataSource<List<ParamFlowRule>> paramFlowRuleWds = new FileWritableDataSource<List<ParamFlowRule>>(paramFlowRulePath,this::enCodeJson);
        ModifyParamFlowRulesCommandHandler.setWritableDataSource(paramFlowRuleWds);
    }

    private Converter<String,List<FlowRule>> flowRuleListParser = source->
        JSON.parseObject(source,new TypeReference<List<FlowRule>>(){});

    private Converter<String,List<DegradeRule>> degradeRuleListParser = source->
            JSON.parseObject(source,new TypeReference<List<DegradeRule>>(){});

    private Converter<String,List<SystemRule>> systemRuleListParser = source->
            JSON.parseObject(source,new TypeReference<List<SystemRule>>(){});

    private Converter<String,List<AuthorityRule>> authorityRuleListParser = source->
            JSON.parseObject(source,new TypeReference<List<AuthorityRule>>(){});

    private Converter<String,List<ParamFlowRule>>paramFlowRuleListParser = source->
            JSON.parseObject(source,new TypeReference<List<ParamFlowRule>>(){});

    private void mkdirIfNotExists(String filePath)throws Exception{
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
    }

    private void createFileIfNotExists(String filePath)throws IOException{
        File file = new File(filePath);
        if (!file.exists()){
            file.createNewFile();
        }
    }
    private <T> String enCodeJson(T t){
        return JSON.toJSONString(t);
    }


}
