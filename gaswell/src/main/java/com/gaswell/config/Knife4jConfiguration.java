package com.gaswell.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfiguration {

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket=new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("gaswell")
                        .description("gaswell")
                        .termsOfServiceUrl("http:")
//                       .contact(new Contact("Find Yourself","yisheng.games","1205325902@qq.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("gaswell后端接口")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.gaswell.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}