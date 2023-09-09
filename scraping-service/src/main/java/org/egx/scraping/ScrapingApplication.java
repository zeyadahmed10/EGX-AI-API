package org.egx.scraping;

import org.egx.scraping.scrapers.NewsScraper;
import org.egx.scraping.scrapers.StockScraper;
import org.egx.scraping.services.StockService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootApplication
public class ScrapingApplication {
    public static void main(String[] args){

        SpringApplication.run(ScrapingApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(StockService stockService){
        return args -> {
            stockService.getUpdatedStockList();
        };
    }
}
