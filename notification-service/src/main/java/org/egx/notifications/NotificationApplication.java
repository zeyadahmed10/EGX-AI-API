package org.egx.notifications;


import org.egx.notifications.dto.SubscriptionRequest;
import org.egx.notifications.entity.Equity;
import org.egx.notifications.repos.EquityRepository;
import org.egx.notifications.services.StockSubscriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class NotificationApplication {

    public static void main(String[] args){
        SpringApplication.run(NotificationApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(StockSubscriptionService service, EquityRepository repo){
        return args ->{
            repo.save(Equity.builder().ISN("isn").name("test").listingDate("date").sector("sector").reutersCode("test").build());
            service.subscribe("zeyad@gmail.com",new SubscriptionRequest("test","news"));
            service.subscribe("zeyad@gmail.com",new SubscriptionRequest("test","stock"));
            service.removeSubscription("zeyad@gmail.com",new SubscriptionRequest("test","lol"));

        };
    }
}
