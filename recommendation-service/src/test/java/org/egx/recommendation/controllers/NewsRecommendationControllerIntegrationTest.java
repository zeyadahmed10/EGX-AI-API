package org.egx.recommendation.controllers;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.egx.clients.io.NewsDto;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NewsRecommendationControllerIntegrationTest {

    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    String access_token;
    Map<String, Object> requestHeader;

    @Test
    void testGetNewsRecommendations_whenUserIsAuthorized_shouldReturnRecommendedNews() throws JSONException {
        //authorization part
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        access_token = new JSONObject(authResponse.asString()).getString("access_token");
        requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);

        //actual request to recommendation service
        RestAssured.port = port;
        var response = RestAssured.given().headers(requestHeader).when().get("/api/v1/recommendations");
        var actual = JsonPath.from(response.asString()).getList("content",NewsDto.class);
        //assert equal response status is ok
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatusCode() );
        //assert that returned list size equal to request param default size
        Assertions.assertEquals(5, actual.size() );
    }
}