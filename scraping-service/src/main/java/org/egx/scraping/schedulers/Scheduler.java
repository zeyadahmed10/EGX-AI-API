package org.egx.scraping.schedulers;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.manegers.NewsScrapingManager;
import org.egx.scraping.manegers.StockScrapingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private NewsScrapingManager newsScrapingManager;
    @Autowired
    private StockScrapingManager stockScrapingManager;


    @PostConstruct
    public void init() throws IOException {
        //TODO: support multiple threads
//        new Thread(() -> {
//            try {
//                stockScrapingManager.scrapeStocks();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
//
//
//        new Thread(() -> {
//            try {
//                newsScrapingManager.scrapeNews();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).start();
        stockScrapingManager.scrapeStocks();
        newsScrapingManager.scrapeNews();
    }
    @Async
    @Scheduled(cron="0 0 19 * * *",zone="GMT+2:00")
    public void scrapeNewsScheduled() throws IOException {
        newsScrapingManager.scrapeNews();
    }
    @Async
    @Scheduled(cron = "0 0/15 10-14 * * SUN-THU", zone="GMT+2:00")
    public void scrapeStocksScheduled() throws IOException{
            stockScrapingManager.scrapeStocks();
        }
}

