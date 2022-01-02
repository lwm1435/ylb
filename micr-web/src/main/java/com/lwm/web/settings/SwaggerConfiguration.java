package com.lwm.web.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * ClassName:SwaggerConfiguration
 * Date:2021/12/13 17:25
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket docket(){
        //使用swaager2的版本
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //设置api的基本信息
        docket.apiInfo(new ApiInfoBuilder()
                .title("动力节点2111班级盈利宝项目API")
                .version("1.1.0")
                .description("第六阶段盈利宝前后端分离项目：前端是Vue，后端是微服务的SpringBoot+Dubbo")
                .contact(new Contact("动力2111班级","http://www.bjpowernode.com","xxx@bjpowernode.com"))
                .build()
        );
        //设置那些包参与 api文档的生成
        docket = docket.select().apis(RequestHandlerSelectors.basePackage("com.lwm.web.controller")).build();

        return docket;
    }
}
