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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

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
    void testGetUpdatedStockList_whenForLoopSuccess_ShouldReturnListOfSize185() throws IOException {
        Document doc = mock(Document.class);
        doReturn(doc).when(stockScraper).getDocument(any(String.class));
        doReturn(stock).when(stockScraper).parseDataOnPage(any(Document.class));
        var actualList = stockService.getUpdatedStockList();
        assertEquals(185, actualList.size(),
                ()-> "Stock List size expected to be 185 but got: "+actualList.size());
    }
    @Test
    void testGetUpdatedStockList_whenDocumentNull_ShouldThrowAnException() throws IOException {

        doThrow(IOException.class).when(stockScraper).getDocument(any(String.class));
        assertThrows(IOException.class, ()->{stockService.getUpdatedStockList();},()->"Should throw an exception");
    }

}