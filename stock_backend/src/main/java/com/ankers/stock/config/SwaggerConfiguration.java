package com.ankers.stock.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInfo())
                .select()
                // 要扫描的API(Controller)基础包
                .apis(RequestHandlerSelectors.basePackage("com.ankers.stock.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    public ApiInfo buildApiInfo() {
        Contact contact = new Contact("子木Github","https://github.com/ankera","15189120919@163.com");
        return new ApiInfoBuilder()
                .title("今日指数-在线接口API文档")
                .description("这是一个方便前后端开发人员快速了解开发接口需求的在线接口API文档")
                .contact(contact)
                .version("1.0.0")
                .build();
    }
}
