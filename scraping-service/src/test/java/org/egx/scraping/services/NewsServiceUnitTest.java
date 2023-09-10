package org.egx.scraping.services;

import org.assertj.core.util.Arrays;
import org.egx.scraping.IO.News;
import org.egx.scraping.repos.NewsRepository;
import org.egx.scraping.scrapers.NewsScraper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class NewsServiceUnitTest {
    @Mock
    NewsRepository newsRepository;
    @Mock
    NewsScraper newsScraper;
    @InjectMocks
    NewsService newsService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetUpdatedNews_whenDocumentIsNull_shouldThrowAnException() throws IOException {
        Mockito.doReturn(null).when(newsScraper).getDocument(Mockito.anyString());
        assertThrows(NullPointerException.class, ()->{newsService.getUpdatedNews();},()->"Should throw an exception");
    }

    @Test
    void testBuildNewsList_whenGetArticleListOfSizeK_shouldReturnNewsListOfSizeKAndRightReutersCode() {
        //arrange
        List<Pair<String,String>> articleList = new ArrayList<>();
        articleList.add(Pair.of("title","article"));
        articleList.add(Pair.of("title","article"));
        //act
        List<News> newsList = newsService.buildNewsList(articleList, "TAQA");
        //assert
        assertEquals(articleList.size(),newsList.size());
        assertEquals("TAQA", newsList.get(0).getReutersCode());
    }
}