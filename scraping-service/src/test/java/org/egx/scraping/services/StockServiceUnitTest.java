package org.egx.scraping.services;

import org.egx.scraping.IO.Stock;
import org.egx.scraping.scrapers.StockScraper;
import static org.mockito.ArgumentMatchers.any;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class StockServiceUnitTest {
    @Mock
    StockScraper stockScraper;
    @InjectMocks
    StockService stockService;
    Stock stock;
    @BeforeEach
    void setUp() {
        stock = new Stock(0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5,0.5);
    }

    @Test
    void testGetUpdatedStock_whenThrowNoException_ShouldReturnStockPOJO() throws IOException {
        Document doc = mock(Document.class);
        doReturn(doc).when(stockScraper).getDocument(any(String.class));
        doReturn(stock).when(stockScraper).parseDataOnPage(any(Document.class));
        var returnedStock = stockService.getUpdatedStock("TAQA");
        assertEquals("TAQA",returnedStock.getReutersCode());

    }
    @Test
    void testGetUpdatedStockList_whenDocumentNull_ShouldThrowAnException() throws IOException {

        doReturn(null).when(stockScraper).getDocument(any(String.class));
        assertThrows(NullPointerException.class, ()->{stockService.getUpdatedStock(Mockito.anyString());},()->"Should throw an exception");
    }

}