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
                .route(p->p.path("/api/v1/customers/**").uri("lb://customer-service"))
                .route(p->p.path("/api/v1/fraud-check/**").uri("lb://fraud-service"))
                .route(p->p.path("/api/v/notification/**").uri("lb://notification-service"))
                .build();
    }
}
