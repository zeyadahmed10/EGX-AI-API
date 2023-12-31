package org.egx.recommendation.services;

import org.springframework.stereotype.Service;

@Service
public class NewsRecommendationService {
    public void getNewsRecommendations(int recommendation, int page, int size, String userEmail) {
        //TODO get userEmbedding
        //TODO get similar embeddings
        //TODO fetch news from news service
    }
}
