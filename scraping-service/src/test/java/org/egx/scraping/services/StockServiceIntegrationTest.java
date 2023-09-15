package org.egx.scraping.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StockServiceIntegrationTest {

    @Autowired
    private StockService stockService;


    @Test
    void testGetUpdatedStock_whenNoThrows_ShouldReturnStockPOJO() throws IOException {

        var stock = stockService.getUpdatedStock("TAQA");
        assertNotNull(stock);
        assertEquals("TAQA", stock.getReutersCode());

    }

}
