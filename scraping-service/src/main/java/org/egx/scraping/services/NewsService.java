package org.egx.scraping.services;

import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.IO.News;
import org.egx.scraping.repos.NewsRepository;
import org.egx.scraping.scrapers.NewsScraper;
import org.egx.scraping.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class NewsService {
    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private NewsScraper newsScraper;

    public static final String BASE_URL = "https://www.mubasher.info/markets/EGX/stocks/";
    public static final String EXTENSION_URL = "/news";
    public List<News> getUpdatedNews(String reutersCode) throws IOException {

        try {
            var document = newsScraper.getDocument(
                    BASE_URL + reutersCode + EXTENSION_URL);
            List<Pair<String, String>> equityArticles = newsScraper.parseDataOnPage(document);
            List<News> newsList = buildNewsList(equityArticles, reutersCode);
            log.info("parsed news articles for equity code: "+ reutersCode);
            newsRepository.saveAll(newsList);
            return newsList;
        } catch (IOException e) {
            log.info(e.getMessage());
            throw new IOException(e);
        }catch (RuntimeException e){
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
        //TODO schedule news scraping
        //TODO send news Asynchronously
    }
    List<News> buildNewsList(List<Pair<String,String>> equityArticles, String reutersCode){
        List<News> newsList = new ArrayList<News>();
        for(var equityPair : equityArticles){
            newsList.add(new News(reutersCode,equityPair.getFirst(),equityPair.getSecond()));
        }
        return newsList;
    }
}
