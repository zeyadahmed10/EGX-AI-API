package org.egx.recommendation.services;

import exceptions.ResourceNotFoundException;
import org.egx.recommendation.repos.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NewsRecommendationService {
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    public void getNewsRecommendations(int recommendation, int page, int size, String userEmail) {
        //TODO get userEmbedding
        var userEmbedding = userHistoryRepository.findByEmail(userEmail).orElseThrow(
                ()-> new ResourceNotFoundException("No user behavior tracked yet browse some news for better experience")
        ).getEmbedding();
        //TODO get similar embeddings
        //TODO fetch news from news service
    }
}
