package com.denghuaijun.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SmsUtil
 *
 * @author denghuaijun@eversec.cn
 * @date 2022/7/18 16:54
 * @Description 使用阿里云提供的Demo进行测试短信发送
 */
public class SmsUtil {
    //产品名称：云通短信API产品，开发者无需替换
    private static String product="Dysmsapi";
    //产品域名
    private static String domain ="dysmsapi.aliyuncs.com";

    private static String accessKeyId="xxxx";

    private static String accessKeySecret="xxxxxx";
    //短信发送
    public static SendSmsResponse sendSms() throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");
        //初始化acsClient，暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou",product,domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();
        //必填：手机号
        request.setPhoneNumbers("15117975359");
        //必填：短信签名
        request.setSignName("云通信");
        //必填：短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_246225392");
        //发送模板参数值
        request.setTemplateParam("{\"code\":\"1234\"}");
        request.setOutId("12321321");
        SendSmsResponse acsResponse = acsClient.getAcsResponse(request);
        return acsResponse;
    }
    //短信查询
    public static QuerySendDetailsResponse querySendDetails(String bizId) throws ClientException{
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout","10000");
        System.setProperty("sun.net.client.defaultReadTimeout","10000");
        //初始化acsClient，暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",accessKeyId,accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou",product,domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        request.setPhoneNumber("15117975359");
        request.setBizId(bizId);
        //必填-发送日期，支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(format.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填页码
        request.setCurrentPage(1L);
        QuerySendDetailsResponse acsResponse = acsClient.getAcsResponse(request);
        return acsResponse;
    }

    public static void main(String[] args) throws ClientException {
        //发送短信
        SendSmsResponse sendSmsResponse = sendSms();
        System.out.println("发送短信返回信息："+sendSmsResponse);
    }
}
