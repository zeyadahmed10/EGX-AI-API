package org.egx.recommendation.services;

import org.egx.recommendation.client.NewsClient;
import org.egx.recommendation.entity.NewsEmbedding;
import org.egx.recommendation.entity.UserHistory;
import org.egx.recommendation.repos.NewsEmbeddingRepository;
import org.egx.recommendation.repos.UserHistoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class NewsRecommendationServiceUnitTest {

    List<NewsEmbedding> topHitsList;
    List<NewsEmbedding> userBehaviorList;
    @InjectMocks
    private NewsRecommendationService newsRecommendationService;
    @Mock
    private UserHistoryRepository userHistoryRepository;
    @Mock
    private NewsEmbeddingRepository newsEmbeddingRepository;
    @Mock
    private NewsClient newsClient;

    @BeforeEach
    void setUp() {
        topHitsList = new ArrayList<NewsEmbedding>();
        userBehaviorList = new ArrayList<NewsEmbedding>();
        for(int i = 1; i<=5; i++){
            var item = new NewsEmbedding(i, 0, new ArrayList<Double>());
            if(i>2)
                userBehaviorList.add(item);
            else
                topHitsList.add(item);
        }
    }

    @Test
    void testGetNewsRecommendations_whenArgsProvidedWithAuthenticatedUserAndHaveHistory_shouldReturnNewDtoPageWithRecommendedNewsBasedOnBehavior() {
        var user = new UserHistory(1, "user@email.com", new ArrayList<Double>());
        doReturn(Optional.of(user)).when(userHistoryRepository).findByEmail(any(String.class));
        doReturn(userBehaviorList).when(newsEmbeddingRepository)
                .findKNearestNeighbors(any(String.class),any(Integer.class),any(Integer.class));
        var actualIds = newsRecommendationService.getNewsIdsList("user@email.com", 1, 1);
        doReturn(new PageImpl<>(userBehaviorList)).when(newsClient).getNewsByIds(Mockito.<Integer>anyList(),anyInt(), anyInt());
        var actual = newsRecommendationService.getNewsRecommendations(1, 1, "user@email.com");
        var expected = new PageImpl<>(userBehaviorList);
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void testGetNewsRecommendations_whenArgsProvidedWithAuthenticatedUserAndDoesNotHaveHistory_shouldReturnNewDtoPageWithRecommendedNewsBasedOnTopHits() {
        doReturn(Optional.empty()).when(userHistoryRepository).findByEmail(any(String.class));
        doReturn(new PageImpl<>(topHitsList)).when(newsEmbeddingRepository).findAllByOrderByHitsDesc(any(Pageable.class));
        var actualIds = newsRecommendationService.getNewsIdsList("user@email.com", 1, 1);
        doReturn(new PageImpl<>(topHitsList)).when(newsClient).getNewsByIds(Mockito.<Integer>anyList(),anyInt(), anyInt());
        var actual = newsRecommendationService.getNewsRecommendations(1, 1, "user@email.com");
        var expected = new PageImpl<>(topHitsList);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetNewsIdsList_whenUserEmailProvidedDoesNotHaveHistory_shouldReturnListOfTopHitsIds() {
        doReturn(Optional.empty()).when(userHistoryRepository).findByEmail(any(String.class));
        doReturn(new PageImpl<>(topHitsList)).when(newsEmbeddingRepository).findAllByOrderByHitsDesc(any(Pageable.class));
        var actual = newsRecommendationService.getNewsIdsList("notExisted@email.com", 1, 1);
        var expected = topHitsList.stream().map(NewsEmbedding::getId).toList();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void testGetNewsIdsList_whenUserEmailProvidedHaveHistory_shouldReturnListOfRecommendedNewsByHistory() {
        var user = new UserHistory(1, "user@email.com", new ArrayList<Double>());
        doReturn(Optional.of(user)).when(userHistoryRepository).findByEmail(any(String.class));
        doReturn(userBehaviorList).when(newsEmbeddingRepository)
                .findKNearestNeighbors(any(String.class),any(Integer.class),any(Integer.class));
        var actual = newsRecommendationService.getNewsIdsList("user@email.com", 1, 1);
        var expected = userBehaviorList.stream().map(NewsEmbedding::getId).toList();
        Assertions.assertEquals(expected, actual);
    }
}