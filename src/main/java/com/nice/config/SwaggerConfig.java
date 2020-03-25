package com.nice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration @EnableSwagger2 public class SwaggerConfig {

    @Bean public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build();
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo =
            new ApiInfo("Real Sucos API REST", "API REST Real Sucos", "1.0", "Terms of Service",
                new Contact("", "r", ""), "Apache License Version 2.0",
                "https://www.apache.org/licesen.html", new ArrayList<VendorExtension>());

        return apiInfo;
    }
}
