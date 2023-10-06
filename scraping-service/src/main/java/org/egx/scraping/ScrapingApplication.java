package org.egx.scraping;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
@EnableScheduling
@EnableAsync
@SpringBootApplication
@EnableDiscoveryClient
public class ScrapingApplication {
    public static void main(String[] args){

        SpringApplication.run(ScrapingApplication.class, args);
    }
//    @Bean
//    CommandLineRunner commandLineRunner(NewsRepository newsRepository){
//        return args -> {
//            newsRepository.save(new News("code","title","article"));
//        };
//    }
}
