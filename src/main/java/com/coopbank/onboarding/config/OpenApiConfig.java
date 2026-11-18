package com.coopbank.onboarding.config;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI bankingApi() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title(
                                        "Digital Banking Platform API"
                                )
                                .version(
                                        "1.0"
                                )
                                .description(
                                        "Customer onboarding and banking backend"
                                )
                );
    }
}