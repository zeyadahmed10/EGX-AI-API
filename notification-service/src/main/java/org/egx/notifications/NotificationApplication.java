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

//    @Bean
//    CommandLineRunner commandLineRunner(StockSubscriptionService service, EquityRepository repo, SubscribedUserRepository repo2){
//        return args ->{
//            repo.save(Equity.builder().ISN("isn").name("test").listingDate("date").sector("sector").reutersCode("test").build());
//            repo.save(Equity.builder().ISN("isn").name("test2").listingDate("date").sector("sector").reutersCode("test2").build());
//            service.subscribe("zeyad@gmail.com",new SubscriptionRequest("test","stock"));
//            service.subscribe("hesham@gmail.com",new SubscriptionRequest("test","stock"));
//            service.subscribe("zeyad2@gmail.com",new SubscriptionRequest("test2","stock"));
//            service.subscribe("hesham2@gmail.com",new SubscriptionRequest("test2","stock"));
//            System.out.println("test");
//            System.out.println(repo2.findSubscribedUsersByReutersCode("test"));
//            System.out.println("test2");
//            System.out.println(repo2.findSubscribedUsersByReutersCode("test2"));
//        };
//    }
}
