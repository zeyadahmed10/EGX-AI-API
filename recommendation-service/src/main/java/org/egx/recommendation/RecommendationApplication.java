package org.egx.recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RecommendationApplication {

    public static void main(String[] args){
        SpringApplication.run(RecommendationApplication.class, args);
    }
}
