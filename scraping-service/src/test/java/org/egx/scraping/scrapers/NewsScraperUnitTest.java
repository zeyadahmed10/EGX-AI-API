package org.egx.scraping.scrapers;

import org.egx.scraping.IO.News;
import org.egx.scraping.repos.NewsRepository;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class NewsScraperUnitTest {
    @Mock
    NewsRepository newsRepository;
    @InjectMocks
    NewsScraper newsScraper;
    Document document;
    Document subDocument;
    String docUrl= "https://www.mubasher.info/markets/EGX/stocks/DCRC/news";
    String subDocUrl= "https://www.mubasher.info/news/4090608/%D9%83%D9%88%D8%A8%D8%B1-%D9%84%D9%84%D8%A7%D8%B3%D8%AA%D8%AB%D9%85%D8%A7%D8%B1-%D8%A7%D9%84%D8%AA%D8%AC%D8%A7%D8%B1%D9%8A-%D8%AA%D9%82%D9%84%D8%B5-%D8%AE%D8%B3%D8%A7%D8%A6%D8%B1%D9%87%D8%A7-91-%D8%AE%D9%84%D8%A7%D9%84-2022/";
    @BeforeEach
    void setUp() {
        try{
            document = newsScraper.getDocument(docUrl);
            subDocument = newsScraper.getDocument(subDocUrl);
        }catch (IOException e){
            throw new RuntimeException(e.getMessage());
        }
    }
    @Test
    void testGetDocument_whenUrlIsNull_shouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->newsScraper.getDocument(""));
        String expectedMessage = "The 'url' parameter must not be empty.";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());

    }
    @Test
    void testGetDocument_whenUrlIsWrong_shouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->newsScraper.getDocument("test"));
        String expectedMessage = "The supplied URL, 'test', is malformed. Make sure it is an absolute URL, and starts with 'http://' or 'https://'. See https://jsoup.org/cookbook/extracting-data/working-with-urls";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());

    }
    @Test
    void testParseDataOnPage_whenDocumentIsNotNull_ShouldNotThrowAnException() {
        boolean isExisted = Mockito.doReturn(false).when(newsRepository).existsByTitle(Mockito.any(String.class));
        assertDoesNotThrow(()->newsScraper.parseDataOnPage(document));
    }
    @Test
    void testParseDataOnPage_whenDocumentIsNull_ShouldThrowAnException() {
        Exception exception = assertThrows(RuntimeException.class, ()->newsScraper.parseDataOnPage(null));
        String expectedMessage = "Cannot invoke \"org.jsoup.nodes.Document.select(String)\" because \"document\" is null";
        assertEquals(expectedMessage, exception.getMessage(), ()->"Expected: "+expectedMessage+" but got: "+exception.getMessage());
    }

    @Test
    void testGetNewsArticles_whenDocumentIsNotNull_shouldReturnElementNotNullOrEmpty() {
        Elements newsArticles = NewsScraper.getNewsArticles(document);
        assertNotEquals(null, newsArticles, ()->"Expected articles but got null value");
        assertNotEquals(0,newsArticles.size(), ()->"Expected articles but got zero articles");
    }
    @Test
    void testGetNewsArticles_whenDocumentIsNull_shouldThrowAnException() {
        assertThrows(RuntimeException.class, ()->{NewsScraper.getNewsArticles(null);});

    }

    @Test
    void testGetNewsAnchor_whenNewsIsNullOrEmpty_shouldThrowAnException() {
        assertThrows(RuntimeException.class, ()->{NewsScraper.getNewsAnchor(null);});
    }

    @Test
    void testGetResourceUrl_whenAnchorsIsNullOrEmpty_ShouldThrowAnException() {
        assertThrows(RuntimeException.class, ()->{NewsScraper.getResourceUrl(null);});

    }

    @Test
    void testGetResourceLines_whenSubDocumentIsNotNull_shouldReturnElementNotNullOrEmpty() {
        Elements lines = NewsScraper.getResourceLines(subDocument);
        assertNotEquals(null, lines, ()->"Expected lines but got null value");
        assertNotEquals(0,lines.size(), ()->"Expected lines but got zero lines");
    }
    @Test
    void testGetResourceLines_whenSubDocumentIsNull_shouldThrowAnException() {
        assertThrows(RuntimeException.class, ()->{NewsScraper.getResourceUrl(null);});

    }
}