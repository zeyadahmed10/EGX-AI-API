package org.egx.scraping.manegers;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedStock;
import org.egx.scraping.IO.Stock;
import org.egx.scraping.services.StockService;
import org.egx.scraping.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@Slf4j
public class StockScrapingManager {
    @Autowired
    private KafkaTemplate<String, ScrapedStock> kafkaStockTemplate;

    @Autowired
    private StockService stockService;

    public void scrapeStocks() throws IOException {
        var equities = Utils.readEquities("equities.txt");
        for (var eq : equities) {
            Stock stock = stockService.getUpdatedStock(eq.get(3));
            ScrapedStock scrapedStock = ScrapedStock.builder()
                    .reutersCode(stock.getReutersCode())
                    .currPrice(stock.getCurrPrice())
                    .rateOfChange(stock.getRateOfChange())
                    .percentageOfChange(stock.getPercentageOfChange())
                    .open(stock.getOpen())
                    .prevClose(stock.getPrevClose())
                    .highest(stock.getHighest())
                    .lowest(stock.getLowest())
                    .volume(stock.getVolume())
                    .value(stock.getValue())
                    .time(stock.getTime())
                    .build();
            kafkaStockTemplate.send("scrapedStocks", scrapedStock);
            log.info("kafka sent stock successfully :" + scrapedStock.toString());
        }
    }
}
