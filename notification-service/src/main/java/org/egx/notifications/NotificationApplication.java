package org.egx.notifications;


import org.egx.notifications.entity.StockSubscription;
import org.egx.notifications.repos.StockSubscriptionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class NotificationApplication {

    public static void main(String[] args){
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StockSubscriptionRepository repo){
        return args -> {
            var data = StockSubscription.builder().
                    reutersCode("iss").subscribedUsers(new ArrayList<String>(Arrays.asList("zeyad@gmail.com","hesham@gmail.com")))
                            .build();

            repo.insert(data);
            System.out.println("we are here");
            System.out.println(data);
        };
    }
}
