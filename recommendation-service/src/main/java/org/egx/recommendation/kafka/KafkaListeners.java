package org.egx.recommendation.kafka;


import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    @KafkaListener(topics="scrapedNews", groupId = "recommendation-service-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedNews"})
    void listener(ScrapedNews scrapedNews){
        String text = "Code: " ;
    }
}
