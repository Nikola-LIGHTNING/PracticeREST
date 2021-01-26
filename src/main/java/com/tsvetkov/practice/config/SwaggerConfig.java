package com.tsvetkov.practice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@Controller
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.api.title}")
    private String title;

    @Value("${swagger.api.description}")
    private String description;

    @Value("${swagger.api.version}")
    private String version;

    @Value("${swagger.api.controller.basepackage}")
    private String basePackage;

    @Value("${email}")
    private String email;

    @RequestMapping(value = "api/docs", method = RequestMethod.GET)
    public String docs() {
        return "redirect:/swagger-ui.html";
    }

    @Bean
    public Docket swaggerSpringFoxConfiguration() {

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(metaData()).select()
                .apis(RequestHandlerSelectors.basePackage(basePackage)).paths(regex("/api/.*")).build();
    }

    private ApiInfo metaData() {
        Contact contact = new Contact("Nikola", "www.swagger.io", email);
        return new ApiInfoBuilder().contact(contact).title(title).description(description).version(version).build();
    }


}
