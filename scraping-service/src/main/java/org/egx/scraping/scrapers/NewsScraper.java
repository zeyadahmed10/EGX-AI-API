package org.egx.scraping.scrapers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class NewsScraper {

    Document document;

    public NewsScraper() throws IOException {
        document = Jsoup.connect("https://www.mubasher.info/markets/EGX/stocks/TAQA/news")
                .userAgent("Mozilla/5.0")
                .get();
    }
    public void parseDataOnPage() throws IOException{
        var news = document.select(".mi-article-media-block.md-whiteframe-z1");
        var item = news.get(0);
        var anchor = item.select("a.mi-article-media-block__title");
        var url = anchor.attr("href");
        System.out.println(url);
        //title
        System.out.println(item.select("div.mi-hide-for-small.mi-article-media-block__text").text());
        String baseUrl = "https://www.mubasher.info";
        Document subDocNews = Jsoup.connect(baseUrl+url)
                .userAgent("Mozilla/5.0")
                .get();
        var article = subDocNews.select("div.article__content-text");
        //System.out.println(article.size());
        //System.out.println(article);
        //System.out.println(article.select("div").size());
        var lines = article.select("p");
        StringBuilder newArticle = new StringBuilder();
        for(var line: lines){
            if(line.getElementsByTag("a").size()>0)
                continue;
            if(line.text()=="ترشيحات")
                continue;
            newArticle.append(line.text());
        }
        System.out.println(newArticle.toString());


    }
}
