package org.egx.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
@EntityScan(basePackages = {"org.egx.clients.io"})
public class NewsApplication {
    public static void main(String []args){
        SpringApplication.run(NewsApplication.class, args);
    }
}
