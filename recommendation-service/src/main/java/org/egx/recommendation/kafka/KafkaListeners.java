package org.egx.recommendation.kafka;


import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.TranslateException;
import exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.NewsDto;
import org.egx.clients.io.UserBehaviorEvent;
import org.egx.recommendation.entity.NewsEmbedding;
import org.egx.recommendation.entity.UserHistory;
import org.egx.recommendation.repos.NewsEmbeddingRepository;
import org.egx.recommendation.repos.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

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
        String userEmail = userBehaviorEvent.getUserEmail();
        Integer newsId = userBehaviorEvent.getNews().getId();
        var newsEntity = newsEmbeddingRepository.findById(newsId).orElseThrow(
                    ()-> {
                        log.error("Could not find embedding for news with id " + newsId);
                        return new ResourceNotFoundException("Could not find embedding for news with id: "+newsId);
                    }
            );
        newsEntity.setHits(newsEntity.getHits()+1);
        newsEmbeddingRepository.save(newsEntity);
        if(!userHistoryRepository.existsByEmail(userBehaviorEvent.getUserEmail())){
            userHistoryRepository.save(UserHistory.builder().email(userEmail).embedding(newsEntity.getEmbedding()).build());
            return;
        }
        var userHistoryEntity = userHistoryRepository.findByEmail(userEmail).get();
        var updatedEmbedding = smoothUserEmbedding(newsEntity.getEmbedding(),userHistoryEntity.getEmbedding());
        //TODO: update user embedding
        List<Double> updatedUserEmbedding = DoubleStream.of(updatedEmbedding).boxed().collect(Collectors.toList());
        userHistoryEntity.setEmbedding(updatedUserEmbedding);
        userHistoryRepository.save(userHistoryEntity);
        log.info("User history updated");
    }
    @KafkaListener(topics="news-vectorized", groupId = "recommendation-service-group",
            containerFactory = "kafkaNewsListenerContainerFactory",properties = {"spring.json.value.default.type=org.egx.clients.io.NewsDto"})
    void newsListener(NewsDto news) throws TranslateException {
        if(newsEmbeddingRepository.existsById(news.getId()))
            return;
        String toBeTranslated = "name: "+news.getName()+", sector: "+news.getSector()+", title: "+news.getTitle()+", topic: "+news.getArticle();
        var rawEmbedding = sentenceTransformerPredictor.predict(toBeTranslated);
        List<Double> embedding = new ArrayList<>(Arrays.asList(getFloatArrayAsDouble(rawEmbedding)));
        newsEmbeddingRepository.save(new NewsEmbedding(news.getId(), 0, embedding));
        log.info("News with id: "+news.getId() +" embedding is saved");
    }
    public static Double getFloatAsDouble(float value){
        return Double.valueOf(Float.valueOf(value).toString());
    }
    public static Double[] getFloatArrayAsDouble(float[] array) {
        Double[] result = new Double[array.length];
        for(int i = 0; i < result.length; i++){
            result[i] = getFloatAsDouble(array[i]);
        }
        return result;
    }
    public static float[] getDoubleListAsFloat(List<Double> list){
        float[] result = new float[list.size()];
        for(int i = 0; i < result.length; i++){
            result[i] = list.get(i).floatValue();
        }
        return result;
    }
    public double[] smoothUserEmbedding(List<Double> newsEmbedding, List<Double> userHistoryEmbedding){
        var NDmanager = NDManager.newBaseManager();
        var NDNewsEmbedding = NDmanager.create(getDoubleListAsFloat(newsEmbedding));
        var NDUserEmbedding = NDmanager.create(getDoubleListAsFloat(userHistoryEmbedding));
        NDNewsEmbedding.mul(0.2);
        NDUserEmbedding.mul(0.8);
        NDUserEmbedding.add(NDNewsEmbedding);
        return NDUserEmbedding.toDoubleArray();
    }
}
