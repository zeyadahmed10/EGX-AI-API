package org.egx.scraping.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;


    @Test
    void testGetUpdatedStockList_whenForLoopSuccess_ShouldReturnListOfSize185() throws IOException {
        var actualList = stockService.getUpdatedStockList();
        assertEquals(185, actualList.size(),
                ()-> "Stock List size expected to be 185 but got: "+actualList.size());
    }

}
