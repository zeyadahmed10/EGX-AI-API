package org.egx.notifications;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class NotificationApplication {

    public static void main(String[] args){
        SpringApplication.run(NotificationApplication.class, args);
    }
}
