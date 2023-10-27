package com.anu.proj.youtech.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket=new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());

        docket.securityContexts(Arrays.asList(getSecurityContexts()));
        docket.securitySchemes(Arrays.asList(getSecuritySchemes()));

        ApiSelectorBuilder select=docket.select();
        select.apis(RequestHandlerSelectors.any());
        select.paths(PathSelectors.any());
        Docket docket1=select.build();
        return docket1;
    }

    private SecurityContext getSecurityContexts() {

        SecurityContext context= SecurityContext
                .builder()
                .securityReferences(getSecurityReferences())
                .build();
        return context;
    }

    private List<SecurityReference> getSecurityReferences() {

        AuthorizationScope[] scopes={
                new AuthorizationScope("Global","Access Everything")
        };

        return Arrays.asList(new SecurityReference("Apikey",scopes));
    }

    private ApiKey getSecuritySchemes() {
        return new ApiKey("Apikey","Authorization","header");
    }


    private ApiInfo getApiInfo() {

        ApiInfo apiInfo=new ApiInfo(
                "YouStore : Backend || APIs",
                "This is the backend for an e-commerce(electronic store) application",
                "1.0.0",
                "https://www.youstore.com",
                new Contact("Anunoy","https://www.linkedin.com/in/anunoy-maji-093a6420a/","anunoy86@gmail.com"),
                "Liscenese of APIs",
                "https://www.youstore.com",
                new ArrayList<>()
        );

        return apiInfo;
    }
}
