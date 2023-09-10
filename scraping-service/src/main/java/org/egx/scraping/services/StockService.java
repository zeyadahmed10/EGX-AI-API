package org.egx.scraping.services;

import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.IO.Stock;
import org.egx.scraping.scrapers.StockScraper;
import org.egx.scraping.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class StockService {
    @Autowired
    private StockScraper stockScraper;
    public static final String BASE_URL = "https://www.mubasher.info/markets/EGX/stocks/";
    public List<Stock> getUpdatedStockList() throws IOException {
        List<Stock> stocks = new ArrayList<Stock>();
        var equities = Utils.readEquities("equities.txt");
        try{
            for(var eq: equities) {
                var document = stockScraper.getDocument(BASE_URL + eq.get(3));
                var stock = stockScraper.parseDataOnPage(document);
                stock.setReutersCode(eq.get(3));
                log.info(stock.toString());
                stocks.add(stock);
            }
            return stocks;
            //TODO schedule stock scraping
            //TODO send stock Asynchronously
        }catch(IOException ex){
            log.error("Could not connect to the host: "+ex.getMessage());
            throw new IOException(ex.getMessage());
        }catch (NullPointerException ex){
            log.error(ex.getMessage());
            throw new NullPointerException(ex.getMessage());
        }
        catch (RuntimeException ex){
            log.error(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }
}
