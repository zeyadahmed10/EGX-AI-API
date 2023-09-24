package org.egx.stocks.kafka;


import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedStock;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    @KafkaListener(topics="scrapedStock", groupId = "stock-service-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedStock"})
    void listener(ScrapedStock scrapedStock){

    }
}
