package org.egx.scraping.kafkaconfig;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.egx.clients.io.ScrapedNews;
import org.egx.kafkaio.serialization.ScrapedNewsSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaScrapedNewsProducerConfig {
    @Value("${spring.kafka.bootstrap.servers}")
    private String bootstrapServers;
    public Map<String, Object> produceConfigs(){
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, String.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,ScrapedNewsSerializer.class);
        return props;
    }
    @Bean
    public ProducerFactory<String, ScrapedNews> producerFactory(){
        return new DefaultKafkaProducerFactory<>(produceConfigs());
    }

    @Bean
    public KafkaTemplate<String, ScrapedNews> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }
}
