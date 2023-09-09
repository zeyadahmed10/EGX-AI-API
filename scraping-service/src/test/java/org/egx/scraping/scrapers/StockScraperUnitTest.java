package org.egx.scraping.scrapers;

import org.jsoup.nodes.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockScraperUnitTest {
    StockScraper stockScraper;
    Document document;
    @BeforeEach
    void setUp() throws IOException {
        try{
            stockScraper = new StockScraper();
            document = stockScraper.getDocument("https://www.mubasher.info/markets/EGX/stocks/DCRC");

        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    void testGetDocument_whenUrlIsWrongOrEmpty_shouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->stockScraper.getDocument("test"));
        String expectedMessage = "The supplied URL, 'test', is malformed. Make sure it is an absolute URL, and starts with 'http://' or 'https://'. See https://jsoup.org/cookbook/extracting-data/working-with-urls";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());

    }

    @Test
    void testParseDataOnPage_whenDocumentIsNotNull_ShouldNotThrowAnException() {
        assertDoesNotThrow(()->stockScraper.parseDataOnPage(document));
    }
    @Test
    void testParseDataOnPage_whenDocumentIsNull_ShouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->stockScraper.parseDataOnPage(null));
        String expectedMessage = "Cannot invoke \"org.jsoup.nodes.Document.select(String)\" because \"document\" is null";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());
    }
    @Test
    void testGetCurrPrice_WhenDocumentIsNull_ShouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->{
          StockScraper.getCurrPrice(null);
        });
        String expectedMessage = "Cannot invoke \"org.jsoup.nodes.Document.select(String)\" because \"document\" is null";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());
    }
    @Test
    void testGetCurrPrice_WhenDocumentIsNotNull_ShouldReturnCurrPriceInDouble() {
        var actualCurrPrice = StockScraper.getCurrPrice(document);
        assertEquals(Double.class, actualCurrPrice.getClass(),()->"Expected Double data type but got "+ actualCurrPrice.getClass());
    }
    @Test
    void testGetCurrPrice_WhenDocumentIsNotNull_ShouldNotThrowException(){
        assertDoesNotThrow(()->StockScraper.getCurrPrice(document));
    }

    @Test
    void testGetRateOfChange_whenDocumentIsNull_shouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class,()->StockScraper.getRateOfChange(null));
        String expectedMessage = "Cannot invoke \"org.jsoup.nodes.Document.select(String)\" because \"document\" is null";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());
    }
    @Test
    void testGetRateOfChange_whenDocumentIsNotNull_ShouldReturnRateOfChangeInDouble(){
        var actualRateOfChange = StockScraper.getRateOfChange(document);
        assertEquals(Double.class, actualRateOfChange.getClass(),()->"Expected Double data type but got "+ actualRateOfChange.getClass());

    }
    @Test
    void testGetRateOfChange_whenDocumentIsNotNull_ShouldNotThrowAnException(){
        assertDoesNotThrow(()->StockScraper.getRateOfChange(document));
    }
    @Test
    void testGetPercentageOfChange_whenDocumentIsNull_shouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class,()->StockScraper.getPercentageOfChange(null));
        String expectedMessage = "Cannot invoke \"org.jsoup.nodes.Document.select(String)\" because \"document\" is null";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());
    }
    @Test
    void testGetPercentageOfChange_whenDocumentIsNotNull_ShouldReturnRateOfChangeInDouble(){
        var actualPercentageOfChange = StockScraper.getPercentageOfChange(document);
        assertEquals(Double.class, actualPercentageOfChange.getClass(),
                ()->"Expected Double data type but got "+ actualPercentageOfChange.getClass());

    }
    @Test
    void testGetPercentageOfChange_whenDocumentIsNotNull_ShouldNotThrowAnException(){
        assertDoesNotThrow(()->StockScraper.getPercentageOfChange(document));
    }

    @Test
    void testGetMarketSummary_whenDocumentIsNull_ShouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->{
            StockScraper.getMarketSummary(null);
        });
        String expectedMessage = "Cannot invoke \"org.jsoup.nodes.Document.select(String)\" because \"document\" is null";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());
    }
    @Test
    void testGetMarketSummary_whenDocumentIsNotNull_ShouldNotThrowAnException() {
        assertDoesNotThrow(()->StockScraper.getMarketSummary(document));
    }
    @Test
    void testGetMarketSummary_whenDocumentIsNotNull_ShouldReturnDoubleListOfSizeSix() {
        var actualList = StockScraper.getMarketSummary(document);
        assertEquals(6, actualList.size(), ()->"ArrayList does not have same size expected 6 but got " +actualList.size());
    }

}