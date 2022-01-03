package com.lwm.dataservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 10:30
 * @description
 */
@ConfigurationProperties("jdwx.sms")//导入配置文件有关属性
@Component//生成spring bean
@Data
public class JdwxSmsConfig {
    private String url;
    private String content;
    private String appkey;
}
