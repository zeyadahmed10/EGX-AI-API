package org.egx.scraping.scrapers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.IO.Stock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
@Data
public class StockScraper{
    public Document getDocument(String url) throws IOException {

        Document document = Jsoup.connect(url).userAgent("Mozilla/5.0").get();
        return document;
    }

    public Stock parseDataOnPage(Document document) throws RuntimeException{
            var currPrice = getCurrPrice(document);
            var rateOfChange = getRateOfChange(document);
            var percentageOfChange = getPercentageOfChange(document);
            var marketSummary = getMarketSummary(document);
            Stock stock = new Stock(currPrice, rateOfChange, percentageOfChange,
                    marketSummary.get(0), marketSummary.get(1), marketSummary.get(2),
                    marketSummary.get(3), marketSummary.get(4), marketSummary.get(5));
            return stock;

    }
    protected static Double getCurrPrice(Document document) throws RuntimeException{
        String value = document.select(".market-summary__last-price").text();
        return Double.parseDouble(value);
    }

   protected static Double getRateOfChange(Document document) throws RuntimeException{
       String value = document.select(".market-summary__change.number").text();
        return Double.parseDouble(value);
   }
   protected static Double getPercentageOfChange(Document document) throws RuntimeException{
        String value =document.select(".market-summary__change-percentage.number").text();
       return Double.parseDouble(value.substring(0, value.length()-1));
   }
   protected static List<Double> getMarketSummary(Document document) throws RuntimeException{
       var strMarketSummary = document.select(".market-summary__block-number").text();
       strMarketSummary = strMarketSummary.replaceAll(",","");
       var arrMarketSummary = strMarketSummary.split(" ");
       //list contains in order
       //Open, P.C, Highest, Lowest, Volume, Value LE
       return Arrays.stream(arrMarketSummary)
               .map(s -> Double.parseDouble(s)).collect(Collectors.toList());
   }
}
