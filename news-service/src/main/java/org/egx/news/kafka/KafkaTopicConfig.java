package org.egx.news.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic userBehaviorTopic(){
        return TopicBuilder.name("user-behavior").build();
    }
    @Bean
    public NewTopic newsVectorizedTopic(){
        return TopicBuilder.name("news-vectorized").build();
    }
}
