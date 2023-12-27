package org.egx.recommendation.kafka;


import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.BaseNews;
import org.egx.clients.io.UserBehaviorEvent;
import org.egx.recommendation.repos.NewsEmbeddingRepository;
import org.egx.recommendation.repos.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    @Autowired
    private Predictor<String, float[]> sentenceTransformerPredictor;
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private NewsEmbeddingRepository newsEmbeddingRepository;
    @KafkaListener(topics="user-behavior", groupId = "recommendation-service-group",
            containerFactory = "kafkaUserBehaviorListenerContainerFactory",properties = {"spring.json.value.default.type=org.egx.clients.io.UserBehaviorEvent"})
    void userBehaviorListener(UserBehaviorEvent userBehaviorEvent){
        System.out.println(userBehaviorEvent);

    }
    @KafkaListener(topics="news-vectorized", groupId = "recommendation-service-group",
            containerFactory = "kafkaBaseNewsListenerContainerFactory",properties = {"spring.json.value.default.type=org.egx.clients.io.UserBehaviorEvent"})
    void baseNewsListener(BaseNews news) throws TranslateException {
        System.out.println(news);
        System.out.println(sentenceTransformerPredictor.predict(news.getArticle()));
    }
}
