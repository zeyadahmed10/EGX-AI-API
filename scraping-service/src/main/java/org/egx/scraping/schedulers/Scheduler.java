package org.egx.scraping.schedulers;

import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.egx.scraping.services.NewsService;
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
    private KafkaTemplate<String, ScrapedNews> kafkaTemplate;
    @Autowired
    private NewsService newsService;


    @Scheduled(cron="0 11 17 * * *",zone="GMT+3:00")
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
                        .date(item.getDate())
                        .build();
                kafkaTemplate.send("scrapedNews",scrapedNews);
                log.info("Kafka sent news successfully: "+scrapedNews.getTitle());
            }
        }
    }
}
