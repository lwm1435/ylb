package com.lwm.dataservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lwm1435@163.com
 * @date 2022-01-03 18:31
 * @description
 */
@ConfigurationProperties("jdwx.real-name")
@Component
@Data
public class JdwxRealNameConfig {
    private String url;
    private String appkey;
}
