package org.egx.news.services;

import exceptions.ResourceNotFoundException;
import org.egx.news.entity.Equity;
import org.egx.news.entity.News;
import org.egx.news.repos.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class NewsServiceUnitTest {
    List<Equity> equityList = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;
    @Mock
    private NewsRepository newsRepository;
    @InjectMocks
    private NewsService newsService;

    @BeforeEach
    void setUp() {
        reutersCode = new String[]{"DCRC", "ATQA"};
        names = new String[]{"Delta Construction & Rebuilding", "Misr National Steel - Ataqa"};
        dates = new String[]{"12/09/1994", "24/05/2006"};
        ISNs = new String[]{"EGS21451C017", "EGS3D0C1C018"};
        sectors = new String[]{"Real Estate", "Basic Resources"};
        for (int i = 0; i < 2; i++) {
            var equity = Equity.builder()
                    .ISN(ISNs[i])
                    .name(names[i])
                    .reutersCode(reutersCode[i])
                    .sector(sectors[i])
                    .listingDate(dates[i]).build();
            equityList.add(equity);
        }

        for (int i = 0; i < 4; i++) {
            int EqIdx = i > 1 ? 1 : 0;
            var news = News.builder()
                    .article("article" + i)
                    .title("title" + i)
                    .time(java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0"))
                    .equity(equityList.get(EqIdx)).build();
            newsList.add(news);
        }
    }


    @Test
    void testFetchNewsAsList_whenDefaultFiltersAndSizeTwo_shouldReturnCorrectNewsListEntitiesWithSizeOfTwo() {
        //arrange
        Pageable pageable = PageRequest.of(0, 2);
        int start = (int) pageable.getOffset();
        int end = Math.min(start+pageable.getPageSize(), newsList.size());
        List<News> pageContent = newsList.subList(start, end);
        var expected = new PageImpl<>(pageContent,pageable,newsList.size());
        //act
        doReturn(expected).when(newsRepository).findAllByFilters(
                any(String.class),any(String.class),any(String.class),any(Pageable.class)
        );
        var actual = newsService.fetchNewsAsList("", "","", 0,2);
        assertEquals(expected, actual);

    }

    @Test
    void testGetNewsById_whenIdProvidedIsCorrect_shouldReturnNewsObject() {
        Optional<News> expectedNews = Optional.of(newsList.get(0));
        doReturn(expectedNews).when(newsRepository).findById(any(Integer.class));
        var realNews = newsService.getNewsById(0);
        assertEquals(expectedNews.get(), realNews);
    }
    @Test
    void testGetNewsById_whenIdProvidedIsNotCorrect_shouldThrowResourceNotFoundException() {
        Optional<News> expectedNews = Optional.empty();
        doReturn(expectedNews).when(newsRepository).findById(any(Integer.class));
        assertThrows(ResourceNotFoundException.class,()->newsService.getNewsById(5));
    }

}