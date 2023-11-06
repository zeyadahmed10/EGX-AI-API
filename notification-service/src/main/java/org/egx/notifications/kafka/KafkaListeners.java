package org.egx.notifications.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedNews;
import org.egx.clients.io.ScrapedStock;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {

    @KafkaListener(containerFactory ="stockKafkaListenerContainerFactory",topics="scrapedStocks", groupId = "notification-service-stock-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedStock"})
    void stockListener(ScrapedStock scrapedStock) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println("got stock");
        //TODO: notify subscribers
    }

    @KafkaListener(containerFactory ="newsKafkaListenerContainerFactory",topics="scrapedNews", groupId = "notification-service-news-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedNews"})
    void newsListener(ScrapedNews scrapedNews) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        System.out.println("got news");
        //TODO: notify subscribers
    }
}
