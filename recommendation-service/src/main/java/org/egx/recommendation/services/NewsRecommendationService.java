package org.egx.recommendation.services;

import org.egx.recommendation.repos.NewsEmbeddingRepository;
import org.egx.recommendation.repos.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsRecommendationService {
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private NewsEmbeddingRepository newsEmbeddingRepository;
    public void getNewsRecommendations(int recommendation, int page, int size, String userEmail) {
        //if user doesnt have embedding get highest hits news
        var userEntity = userHistoryRepository.findByEmail(userEmail);
        if(userEntity.isEmpty()){
            return;
        }
        var similarNews = newsEmbeddingRepository.findKNearestNeighbors(userEmail, size);
        //TODO get similar news

        //TODO fetch news from news service
    }
}
