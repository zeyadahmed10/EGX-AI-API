package org.egx.news.controlles;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.egx.news.entity.Equity;
import org.egx.news.entity.News;
import org.egx.news.repos.EquityRepository;
import org.egx.news.repos.NewsRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NewsControllerIntegrationTest {
    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    List<Equity> equityList = new ArrayList<>();
    List<News> newsList = new ArrayList<>();
    String[] reutersCode, names, dates, ISNs, sectors;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private EquityRepository equityRepository;

    @BeforeEach
    void createCustomNewsForTesting(){
        reutersCode = new String[]{"DCRC", "ATQA"};
        names = new String[]{"Delta Construction & Rebuilding","Misr National Steel - Ataqa"};
        dates = new String[]{"12/09/1994","24/05/2006"};
        ISNs = new String[]{"EGS21451C017","EGS3D0C1C018"};
        sectors = new String[]{"Real Estate","Basic Resources"};
        equityList.add(equityRepository.findByReutersCode(reutersCode[0]).get());
        equityList.add(equityRepository.findByReutersCode(reutersCode[1]).get());

        for(int i = 0; i<4; i++){
            int EqIdx = i>1 ? 1:0;
            var news = News.builder()
                    .article("article" + i)
                    .title("title" + i)
                    .time(java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0"))
                    .equity(equityList.get(EqIdx)).build();
            newsList.add(news);
        }
        equityList.get(0).setNews(Arrays.asList(newsList.get(0), newsList.get(1)));
        equityList.get(1).setNews(Arrays.asList(newsList.get(2), newsList.get(3)));
        newsRepository.saveAll(newsList);

    }
    @AfterEach
    void deleteCustomNewsList(){
        newsRepository.deleteAll(newsList);
    }
    @Test
    void testFetchNewsAsList_whenEveryServiceWorkProperly_shouldReturn() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        var response = RestAssured.get("/api/v1/news");
        assertEquals(200, response.getStatusCode());
    }
    @Test
    void testFetchNewsAsList_whenEveryServiceWorkProperlyAndCategoryParamsProvided_shouldReturnNewsWithProvidedCategory() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String params="?categoryFilter=real Estate";
        var response = RestAssured.get("/api/v1/news"+params);
        assertEquals(200, response.getStatusCode());
        var news = JsonPath.from(response.asString()).getList("content",News.class);
        List<String> real = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        for(var item: news){
            real.add(item.getEquity().getSector());
            expected.add("Real Estate");
        }
        assertEquals(expected, real);

    }
    @Test
    void testFetchNewsAsList_whenEveryServiceWorkProperlyAndNameParamsProvided_shouldReturnNewsWithProvidedName() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String params="?nameFilter=delta";
        var response = RestAssured.get("/api/v1/news"+params);
        assertEquals(200, response.getStatusCode());
        var news = JsonPath.from(response.asString()).getList("content",News.class);
        List<String> real = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        for(var item: news){
            real.add(item.getEquity().getName());
            expected.add("Delta Construction & Rebuilding");
        }
        assertEquals(expected, real);
    }
    @Test
    void testFetchNewsAsList_whenEveryServiceWorkProperlyAndCodeParamsProvided_shouldReturnNewsWithProvidedCode() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String params="?reutersFilter=DCRC";
        var response = RestAssured.get("/api/v1/news"+params);
        assertEquals(200, response.getStatusCode());
        var news = JsonPath.from(response.asString()).getList("content",News.class);
        List<String> real = new ArrayList<>();
        List<String> expected = new ArrayList<>();
        for(var item: news){
            real.add(item.getEquity().getReutersCode());
            expected.add("DCRC");
        }
        assertEquals(expected, real);
    }
    @Test
    void testGetNews_whenIdProvided_shouldReturnNewsWithProvidedId() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        var response = when().get("/api/v1/news/{id}",1)
                .then().statusCode(200)
                .body("id",equalTo(1));
    }

    @Test
    void deleteNews() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        var response = when().delete("/api/v1/news/{id}",1)
                .then().statusCode(200);
        var news = newsRepository.findById(1);
        assertTrue(news.isEmpty());
    }

}