package org.egx.scraping.kafkaconfig;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic scrapedNewsTopic(){
        return TopicBuilder.name("scrapedNews").build();
    }
    @Bean
    public NewTopic scrapedStocksTopic(){

        return TopicBuilder.name("scrapedStocks").build();
    }
}
