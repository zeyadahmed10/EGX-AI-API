package org.egx.scraping;

import org.egx.scraping.IO.News;
import org.egx.scraping.repos.NewsRepository;
import org.egx.scraping.scrapers.NewsScraper;
import org.egx.scraping.scrapers.StockScraper;
import org.egx.scraping.services.StockService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.SimpleDateFormat;
import java.util.Calendar;
@EnableScheduling
@SpringBootApplication
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
