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
//            System.out.println("we are here");
//            var scrapper = new NewsScraper();
//            scrapper.parseDataOnPage();
            //scrapper.closeDriver();
//            String dateStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
//            String timeStamp = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
//            System.out.println(dateStamp);
//            System.out.println(timeStamp);
            stockService.getUpdatedStockList();
//            var doc = StockScraper.getDocument("https://www.mubasher.info/markets/EGX/stocks/DCRC");
//            var data = StockScraper.parseDataOnPage(doc);
//            System.out.println(data);
        };
    }
}
