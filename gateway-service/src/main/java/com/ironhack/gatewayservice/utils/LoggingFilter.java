package com.ironhack.gatewayservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    //Check logger
    Logger logger = LoggerFactory.getLogger(LoggingFilter.class); // To log actions on the app

    @Override // Mono object, ServerWebExchange, GatewayFilter
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info("Path of the request -> {}", exchange.getRequest().getPath()); // Similar to printf in C, the way arguments are passed
        return chain.filter(exchange);
    }
}
