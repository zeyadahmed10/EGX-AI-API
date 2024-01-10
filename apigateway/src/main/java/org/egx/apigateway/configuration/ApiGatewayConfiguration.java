package org.egx.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder routeLocatorBuilder) throws Exception {



        return routeLocatorBuilder.routes()
                .route(p->p.path("/api/v1/auth/**").uri("lb://auth-service"))
                .route(p->p.path("/api/v1/equities/**").uri("lb://news-service"))
                .route(p->p.path("/api/v1/news/**").uri("lb://news-service"))
                .route(p->p.path("/api/v1/notifications/**").uri("lb://notification-service"))
                .route(p->p.path("/api/v1/recommendations/**").uri("lb://recommendation-service"))
                .route(p->p.path("/api/v1/historical-stocks/**").uri("lb://stocks-service"))
                .route(p->p.path("/api/v/stocks/**").uri("lb://stocks-service"))
                .build();
    }
}
