package org.egx.news.kafka;


import org.egx.clients.io.ScrapedNews;
import org.egx.news.entity.News;
import org.egx.news.services.EquityService;
import org.egx.news.services.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @Autowired
    private EquityService equityService;
    @Autowired
    private NewsService newsService;
    @KafkaListener(topics="scrapedNews")
    void listener(ScrapedNews scrapedNews){
        var equity = equityService.getEquityByCode(scrapedNews.getReutersCode());
        var news = News.builder()
                .title(scrapedNews.getTitle())
                .article(scrapedNews.getArticle())
                .newsData(scrapedNews.getNewsDate())
    }
}
