package org.egx.scraping.services;

import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.IO.Stock;
import org.egx.scraping.scrapers.StockScraper;
import org.egx.scraping.utils.Utils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockService {
    public static final String BASE_URL = "https://www.mubasher.info/markets/EGX/stocks/";
    public List<Stock> getUpdatedStockList() throws IOException {
        List<Stock> stocks = new ArrayList<Stock>();
        var equities = Utils.readEquities();
        int i = 0;
        for(var eq: equities){
            System.out.printf("%d , %s \n",i,BASE_URL+eq.get(3));
            if(i<159){
                i++;
                continue;
            }
            var document = StockScraper.getDocument(BASE_URL+eq.get(3));
            var stock = StockScraper.parseDataOnPage(document);
            stock.setReutersCode(eq.get(3));
            log.info(stock.toString());
            stocks.add(stock);
            i++;
        }
        return stocks;
    }
}
