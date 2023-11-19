package org.egx.stocks.controllers;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.egx.stocks.entity.UpdatedStock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StockControllerIntegrationTest {

    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @Test
    void testFetchAllStocks_whenDefaultParametersProvided_shouldReturnUpdatedStocksAnd200StatusCode(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        var response = RestAssured.get("/api/v1/stocks");
        assertEquals(200,response.getStatusCode());
        List<UpdatedStock> stocks = JsonPath.from(response.asString()).getList("content",UpdatedStock.class);
        assertEquals(10,stocks.size());
    }
    @Test
    void testFetchAllStocks_whenSectorFilterProvided_shouldReturnUpdatedStocksWithDesiredFilterAnd200StatusCode(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String params="?sectorFilter=real Estat";
        var response = RestAssured.get("/api/v1/stocks"+params);
        assertEquals(200,response.getStatusCode());
        List<UpdatedStock> stocks = JsonPath.from(response.asString()).getList("content",UpdatedStock.class);
        assertEquals(10,stocks.size());
        ArrayList<String> expected = new ArrayList<String>();
        ArrayList<String> real = new ArrayList<String>();
        for(var stock: stocks){
            expected.add("Real Estate");
            real.add(stock.getEquity().getSector());
        }
        assertEquals(expected, real);
    }

    @Test
    void testGetUpdatedStockByReutersCode_whenNonExistedCodeProvided_ShouldThrowResourceNotFoundExceptionAnd404statusCode(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String code = "NotExisted";
        var response = RestAssured.get("/api/v1/stocks/"+code);
        assertEquals(404,response.getStatusCode());
    }

    @Test
    void testGetUpdatedStockByReutersCode_whenExistedCodeProvided_ShouldReturnDesiredStockWith200statusCode(){
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
        String code = "SPMD";
        var response = RestAssured.get("/api/v1/stocks/"+code);
        assertEquals(200,response.getStatusCode());
        UpdatedStock stock = response.as(UpdatedStock.class);
        assertEquals(code,stock.getEquity().getReutersCode());
    }
}