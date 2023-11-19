package org.egx.stocks.controllers;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.egx.stocks.entity.OHCLVStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HistoricalStocksControllerIntegrationTest {

    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testGetOHCLVStatistics_whenNoParamsProvided_shouldReturnStockWithDefaultParamsAnd200StatusOk() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String reutersCode = "SPMD";
        var response = RestAssured.get("/api/v1/historical-stocks/"+reutersCode);
        assertEquals(200, response.getStatusCode());
        List<OHCLVStatistics> equities = JsonPath.from(response.asString()).getList("content",OHCLVStatistics.class);
        assertEquals(10,equities.size());
    }
    @Test
    void testGetOHCLVStatistics_whenNonExistedReutersCodeProvided_shouldThrowResourceNotFoundExceptionAnd404NotFoundStatus() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String reutersCode = "lol";
        var response = RestAssured.get("/api/v1/historical-stocks/"+reutersCode);
        assertEquals(404, response.getStatusCode());
        //List<OHCLVStatistics> equities = JsonPath.from(response.asString()).getList("content",OHCLVStatistics.class);
        //assertEquals(10,equities.size());
    }
}