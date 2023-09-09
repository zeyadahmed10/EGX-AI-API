package org.egx.scraping.services;

import org.egx.scraping.scrapers.StockScraper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class StockServiceUnitTest {
    @Mock
    StockScraper stockScraper;
    @InjectMocks
    StockService stockService;
    @BeforeEach
    void setUp() {
        stockService = new StockService();
    }

    @Test
    void getUpdatedStockList() {
    }
}