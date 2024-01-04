package org.egx.recommendation.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.egx.clients.io.NewsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaNewsConsumerConfig {

    @Value("${spring.kafka.bootstrap.servers}")
    private String bootstrapServers;

    public Map<String, Object> consumerConfig(){
        Map<String,Object> props= new HashMap<String,Object>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return props;
    }
    @Bean
    public ConsumerFactory<String, NewsDto> newsConsumerFactory(){
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, NewsDto>> kafkaNewsListenerContainerFactory(ConsumerFactory<String,NewsDto> newsConsumerFactory){
        ConcurrentKafkaListenerContainerFactory<String, NewsDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(newsConsumerFactory);
        return factory;
    }
}
