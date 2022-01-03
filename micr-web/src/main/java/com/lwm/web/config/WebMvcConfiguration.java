package com.lwm.web.config;

import com.lwm.web.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author lwm1435@163.com
 * @date 2021-12-18 21:42
 * @description
 */
@Configuration //相当于SpringMVC配置文件
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Resource
    private TokenInterceptor tokenInterceptor;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //设置拦截路径
        String[] addPath = {"/v1/user/realname","/v1/user/info","/v1/invest/product",
                "/v1/user/record","/v1/user/logout"};
        //注册拦截器
       registry.addInterceptor(tokenInterceptor).addPathPatterns(addPath);
    }

    /**
     * 设置跨域请求
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }

}
