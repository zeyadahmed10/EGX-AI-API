package org.egx.recommendation.services;

import org.egx.clients.io.NewsDto;
import org.egx.recommendation.client.NewsClient;
import org.egx.recommendation.entity.NewsEmbedding;
import org.egx.recommendation.repos.NewsEmbeddingRepository;
import org.egx.recommendation.repos.UserHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsRecommendationService {
    @Autowired
    private UserHistoryRepository userHistoryRepository;
    @Autowired
    private NewsEmbeddingRepository newsEmbeddingRepository;
    @Autowired
    private NewsClient newsClient;
    public Page<NewsDto> getNewsRecommendations(int page, int size, String userEmail) {
        List<Integer> ids = getNewsIdsList(userEmail, page, size);
        return newsClient.getNewsByIds(ids, page, size);
    }
    public List<Integer> getNewsIdsList(String userEmail, int page, int size){
        List<NewsEmbedding> newsEmbeddingList;
        List<Integer> idsList = new ArrayList<Integer>();
        //if user doesnt have enough information restore most read news
        var userEntity = userHistoryRepository.findByEmail(userEmail);
        if(userEntity.isEmpty()){
            Pageable pageable = PageRequest.of(page, size);
            newsEmbeddingList = newsEmbeddingRepository.findAllByOrderByHitsDesc(pageable).getContent();
        }
        else
            newsEmbeddingList = newsEmbeddingRepository.findKNearestNeighbors(userEmail, size, page);
        for(var item: newsEmbeddingList)
            idsList.add(item.getId());
        return idsList;
    }
}
