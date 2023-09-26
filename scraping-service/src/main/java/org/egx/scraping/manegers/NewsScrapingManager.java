package org.egx.scraping.manegers;

import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.egx.scraping.services.NewsService;
import org.egx.scraping.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class NewsScrapingManager {
    @Autowired
    private KafkaTemplate<String, ScrapedNews> kafkaNewsTemplate;
    @Autowired
    private NewsService newsService;

    public void scrapeNews() throws IOException {
        log.info("scraping news started-----------------------------------------------------------");
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
}
