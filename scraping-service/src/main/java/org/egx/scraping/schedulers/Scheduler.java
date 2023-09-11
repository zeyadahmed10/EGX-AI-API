package org.egx.scraping.schedulers;

import org.egx.clients.io.ScrapedNews;
import org.egx.scraping.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Scheduler {

    @Autowired
    private KafkaTemplate<String, ScrapedNews> kafkaTemplate;
    @Autowired
    private NewsService newsService;

    @Scheduled(cron="0 0 17 ? * * *",zone="GMT+3:00")
    public void scrapeNews() throws IOException {
        var newsList = newsService.getUpdatedNews();
        for(var item: newsList){
            ScrapedNews scrapedNews = ScrapedNews.builder()
                    .reutersCode(item.getReutersCode())
                    .title(item.getTitle())
                    .article(item.getArticle())
                    .time(item.getTime())
                    .date(item.getDate())
                    .build();
            kafkaTemplate.send("scrapedNews",scrapedNews);

        }
    }
}
