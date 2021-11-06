package com.ironhack.gatewayservice.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudGatewayConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder){
        return builder.routes()
                        .route(p -> p.path("/lead/**")
                                .uri("lb://lead-service"))
                        .route(p -> p.path("/salesRep/**")
                                .uri("lb://sales-rep-service"))
                        .route(p -> p.path("/opportunities/**")
                                .uri("lb://opportunity-service"))
                        .route(p -> p.path("/account/**")
                                .uri("lb://account-service"))
                        .route(p -> p.path("/contact/**")
                                .uri("lb://contact-service"))
                        .route(p -> p.path("/main/**")
                                .uri("lb://main-service"))
                        .route(p -> p.path("/report/**")
                                .uri("lb://reporting-service"))
                        .build();
    }

}
