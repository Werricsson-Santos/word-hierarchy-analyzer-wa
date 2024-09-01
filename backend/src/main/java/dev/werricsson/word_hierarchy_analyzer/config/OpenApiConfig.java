package dev.werricsson.word_hierarchy_analyzer.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Word Hierarchy Analyzer - RESTful API with Java 17 and Spring Boot 3")
                        .version("v1")
                        .description("Word Hierarchy Analyzer")
                        .termsOfService("https://github.com/Werricsson-Santos/word-hierarchy-analyzer-wa")
                        .license(
                                new License()
                                        .name("Word Hierarchy Analyzer")
                                        .url("https://github.com/Werricsson-Santos/word-hierarchy-analyzer-wa")
                        )
                );
    }
}
