package org.egx.scraping.scrapers;

import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.repos.NewsRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Component
@Slf4j
public class NewsScraper {
    @Autowired
    private NewsRepository newsRepository;

    static String BASE_URL = "https://www.mubasher.info";
    public Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").timeout(0).get();
        return document;
    }
    public List<Pair<String,String>> parseDataOnPage(Document document) throws IOException{
        List<Pair<String,String>> result = new ArrayList();
        var news = getNewsArticles(document);
        for(var item: news){
            var anchor = getNewsAnchor(item);
            var resourceUrl = getResourceUrl(anchor);
            String title = anchor.text();
            if(newsRepository.existsByTitle(title))
                return result;
            Document subDocNews = this.getDocument(BASE_URL+resourceUrl);
            var lines = getResourceLines(subDocNews);
            StringBuilder newArticle = new StringBuilder();
            for(var line: lines){
                if(line.getElementsByTag("a").size()>0)
                    continue;
                if(line.text()=="ترشيحات")
                    continue;
                newArticle.append(line.text()+"\n");
            }
            result.add(Pair.of(title,newArticle.toString()));
        }
        return result;
    }

    static Elements getNewsArticles(Document document){
        return document.select(".mi-article-media-block.md-whiteframe-z1");
    }
    static Elements getNewsAnchor(Element element){
        return element.select("a.mi-article-media-block__title");
    }
    static String getResourceUrl(Elements elements){
        return elements.attr("href");
    }
    static Elements getResourceLines(Document document){
        var article = document.select("div.article__content-text");
        return article.select("p");
    }
}
