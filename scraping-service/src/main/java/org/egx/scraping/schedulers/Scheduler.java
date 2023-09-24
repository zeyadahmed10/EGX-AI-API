package org.egx.scraping.schedulers;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.egx.clients.io.ScrapedStock;
import org.egx.scraping.IO.Stock;
import org.egx.scraping.services.NewsService;
import org.egx.scraping.services.StockService;
import org.egx.scraping.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class Scheduler {

    @Autowired
    private KafkaTemplate<String, ScrapedNews> kafkaNewsTemplate;
    @Autowired
    private KafkaTemplate<String, ScrapedStock> kafkaStockTemplate;
    @Autowired
    private NewsService newsService;
    @Autowired
    private StockService stockService;;
    @PostConstruct
    public void init() throws IOException {
        this.scrapeStocks();
        this.scrapeNews();
    }
    @Scheduled(cron="0 42 19 * * *",zone="GMT+3:00")
    public void scrapeNews() throws IOException {
        var equities = Utils.readEquities("equities.txt");
        for(var eq: equities){
            var newsList = newsService.getUpdatedNews(eq.get(3));
            for(var item: newsList){
                ScrapedNews scrapedNews = ScrapedNews.builder()
                        .reutersCode(item.getReutersCode())
                        .title(item.getTitle())
                        .article(item.getArticle())
                        .time(item.getTime())
                        .build();
                kafkaNewsTemplate.send("scrapedNews",scrapedNews);
                log.info("Kafka sent news successfully: "+scrapedNews.getTitle());
            }
        }
    }

    @Scheduled(cron = "0 0/15 10-15 * * *", zone="GMT+3:00")
    public void scrapeStocks() throws IOException{
        var equities = Utils.readEquities("equities.txt");
        for(var eq: equities){
            Stock stock = stockService.getUpdatedStock(eq.get(3));
            ScrapedStock scrapedStock = ScrapedStock.builder()
                    .reutersCode(stock.getReutersCode())
                    .currPrice(stock.getCurrPrice())
                    .rateOfChange(stock.getRateOfChange())
                    .percentageOfChange(stock.getPercentageOfChange())
                    .open(stock.getOpen())
                    .prevClose(stock.getPrevClose())
                    .highest(stock.getHighest())
                    .lowest(stock.getLowest())
                    .volume(stock.getVolume())
                    .value(stock.getValue())
                    .time(stock.getTime())
                    .build();
            kafkaStockTemplate.send("scrapedStock", scrapedStock);
            log.info("kafka sent stock successfully :"+scrapedStock.toString());
        }
    }
}
