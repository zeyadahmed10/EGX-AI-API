package org.egx.news.kafka;


import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.egx.news.entity.News;
import org.egx.news.services.EquityService;
import org.egx.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    @Autowired
    private EquityService equityService;
    @Autowired
    private NewsService newsService;
    @KafkaListener(topics="scrapedNews", groupId = "news-service-group")
    void listener(ScrapedNews scrapedNews){
        var equity = equityService.getEquityByCode(scrapedNews.getReutersCode());
        var news = News.builder()
                .title(scrapedNews.getTitle())
                .article(scrapedNews.getArticle())
                .newsDate(scrapedNews.getDate())
                .newsTime(scrapedNews.getTime())
                .equity(equity).build();
        newsService.createNews(news);
        log.info("Kafka consumed and saved to DB news with Id: "+news.getId());
    }
}
