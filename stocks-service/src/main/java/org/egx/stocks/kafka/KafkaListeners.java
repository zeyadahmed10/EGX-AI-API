package org.egx.stocks.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.egx.clients.io.ScrapedStock;
import org.egx.stocks.entity.HistoricalStock;
import org.egx.stocks.entity.UpdatedStock;
import org.egx.stocks.repos.EquityRepository;
import org.egx.stocks.repos.HistoricalStockRepository;
import org.egx.stocks.repos.UpdatedStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaListeners {
    @Autowired
    private UpdatedStockRepository updatedStockRepository;
    @Autowired
    private HistoricalStockRepository historicalStockRepository;
    @Autowired
    private EquityRepository equityRepository;
    @KafkaListener(topics="scrapedStocks", groupId = "stocks-service-group", properties = {"spring.json.value.default.type=org.egx.clients.io.ScrapedStock"})
    void listener(ScrapedStock scrapedStock) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var stock = updatedStockRepository.findByEquity_reutersCode(scrapedStock.getReutersCode());
        if(stock.isPresent()){
            //last updated stock before the current one
            var prevUpdatedStock = stock.get();
            String updatedStockString = objectMapper.writeValueAsString(prevUpdatedStock);
            HistoricalStock historicalStock = objectMapper.readValue(updatedStockString, HistoricalStock.class);
            historicalStockRepository.save(historicalStock);
            updatedStockRepository.delete(prevUpdatedStock);
        }
        String stringScrapedStock = objectMapper.writeValueAsString(scrapedStock);
        var equity = equityRepository.findByReutersCode(scrapedStock.getReutersCode());
        var updatedStock = objectMapper.readValue(stringScrapedStock, UpdatedStock.class);
        updatedStock.setEquity(equity.get());
        updatedStockRepository.save(updatedStock);
    }
}
