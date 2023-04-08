package com.digivisions.stc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.RequestHandlerSelectors.any;

@Configuration
@EnableSwagger2
public class CustomConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .forCodeGeneration(true)
                .select()
                .apis(any())
                .paths(PathSelectors.any())
                .build();
    }

}
