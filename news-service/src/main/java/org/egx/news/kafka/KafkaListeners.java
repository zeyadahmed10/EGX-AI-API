package org.egx.news.kafka;


import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.NewsDto;
import org.egx.clients.io.ScrapedNews;
import org.egx.news.entity.News;
import org.egx.news.services.EquityService;
import org.egx.news.services.NewsService;
import org.egx.news.utils.NewsToDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    @Autowired
    private EquityService equityService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private KafkaTemplate<String, NewsDto> kafkaBaseNewsTemplate;
    @KafkaListener(topics="scrapedNews", groupId = "news-service-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedNews"})
    void listener(ScrapedNews scrapedNews){
        var equity = equityService.getEquityByReutersCode(scrapedNews.getReutersCode());
        var news = News.builder()
                .title(scrapedNews.getTitle())
                .article(scrapedNews.getArticle())
                .time(scrapedNews.getTime())
                .equity(equity).build();
        newsService.createNews(news);
        log.info("Kafka consumed and saved to DB news with Id: "+news.getId());
        kafkaBaseNewsTemplate.send("news-vectorized", NewsToDtoMapper.map(news));
        log.info("Base news sent to recommendation service to vectorized");
    }
}
