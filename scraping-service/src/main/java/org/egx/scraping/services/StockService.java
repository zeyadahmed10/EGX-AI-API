package org.egx.scraping.services;

import lombok.extern.slf4j.Slf4j;
import org.egx.scraping.IO.Stock;
import org.egx.scraping.scrapers.StockScraper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Stock getUpdatedStock(String reutersCode) throws IOException {
        try{

            var document = stockScraper.getDocument(BASE_URL + reutersCode);
            var stock = stockScraper.parseDataOnPage(document);
            stock.setReutersCode(reutersCode);
            log.info(stock.toString());
            return stock;
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
