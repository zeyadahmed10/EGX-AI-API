package org.egx.news.repos;

import org.checkerframework.checker.units.qual.A;
import org.egx.news.entity.Equity;
import org.egx.news.entity.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class NewsRepositoryUnitTest {
    List<Equity> equityList = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private EquityRepository equityRepository;

    @BeforeEach
    void setUp() {
        reutersCode = new String[]{"DCRC", "ATQA"};
        names = new String[]{"Delta Construction & Rebuilding","Misr National Steel - Ataqa"};
        dates = new String[]{"12/09/1994","24/05/2006"};
        ISNs = new String[]{"EGS21451C017","EGS3D0C1C018"};
        sectors = new String[]{"Real Estate","Basic Resources"};
        for(int i = 0; i< 2; i++){
            var equity = Equity.builder()
                    .ISN(ISNs[i])
                    .name(names[i])
                    .reutersCode(reutersCode[i])
                    .sector(sectors[i])
                    .listingDate(dates[i]).build();
            equityList.add(equity);
        }

        for(int i = 0; i<4; i++){
            int EqIdx = i>1 ? 1:0;
            var news = News.builder()
                    .article("article" + i)
                    .title("title" + i)
                    .newsDate("date"+i)
                    .newsTime("time"+i)
                    .equity(equityList.get(EqIdx)).build();
            newsList.add(news);
        }
        equityRepository.saveAll(equityList);
        equityList.get(0).setNews(Arrays.asList(newsList.get(0), newsList.get(1)));
        equityList.get(1).setNews(Arrays.asList(newsList.get(2), newsList.get(3)));
        newsRepository.saveAll(newsList);
    }
    @Test
    void testFindAllByFilters_whenCategoryFilterPresented_shouldReturnTheSpecifiedNewsWithRequiredCategory() {
        //arrange
        String equityCategoryFilter = "";
        String equityNameFilter= "";
        String equityReutersFilter= "";
        Pageable pageable = PageRequest.of(0, 2);
        //act
        var resultedNews = newsRepository.findAllByFilters(equityCategoryFilter,
                equityNameFilter, equityReutersFilter, pageable);

        var test = resultedNews.getContent();
        System.out.println(test);
    }



}