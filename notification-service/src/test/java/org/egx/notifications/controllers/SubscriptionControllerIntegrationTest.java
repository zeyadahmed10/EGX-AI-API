package org.egx.notifications.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.egx.notifications.dto.SubscriptionRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class SubscriptionControllerIntegrationTest {

    private final static String BASE_URI = "http://localhost";
    @LocalServerPort
    private int port;
    @BeforeEach
    void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }
    @Test
    void testSubscribe_whenUnauthorizedUser_shouldReturnUnauthorized401statusCode() throws JsonProcessingException {
        var request = RestAssured.given();
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(new SubscriptionRequest("JUFO","stock"));
        request.body(requestBody);
        var response = RestAssured.post("/api/v1/notifications/subscription/subscribe");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }
    @Test
    void testSubscribe_whenWrongSubscriptionTypeEntered_shouldThrowIllegalArgumentExceptionAnd400statusCode() throws JSONException, JsonProcessingException {
        // request to auth-service to get jwt
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        String access_token = new JSONObject(authResponse.asString()).getString("access_token");
        //post request to notification-service
        RestAssured.port = port;
        Map<String,Object> requestBody = new HashMap<String,Object>();
        requestBody.put("reutersCode","JUFO");
        requestBody.put("subscriptionType","NotExisted");
        Map<String, Object> requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);
        var response = RestAssured.given().contentType(ContentType.JSON).body(requestBody)
                .headers(requestHeader)
                .when().post("/api/v1/notifications/subscription/subscribe");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode() );
    }
    @Test
    void testSubscribe_whenNonExistedStockEntered_shouldThrowResourceNotFoundExceptionAnd404statusCode() throws JSONException, JsonProcessingException {
        // request to auth-service to get jwt
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        String access_token = new JSONObject(authResponse.asString()).getString("access_token");
        //post request to notification-service
        RestAssured.port = port;
        Map<String,Object> requestBody = new HashMap<String,Object>();
        requestBody.put("reutersCode","NotExisted");
        requestBody.put("subscriptionType","stock");
        Map<String, Object> requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);
        var response = RestAssured.given().contentType(ContentType.JSON).body(requestBody)
                .headers(requestHeader)
                .when().post("/api/v1/notifications/subscription/subscribe");
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode() );
    }
    @Order(1)
    @Test
    void testSubscribe_whenEnteredValidStockAndSubscriptionType_shouldReturnOk200statusCode() throws JSONException, JsonProcessingException {
        // request to auth-service to get jwt
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        String access_token = new JSONObject(authResponse.asString()).getString("access_token");
        //post request to notification-service
        RestAssured.port = port;
        Map<String,Object> requestBody = new HashMap<String,Object>();
        requestBody.put("reutersCode","JUFO");
        requestBody.put("subscriptionType","stock");
        Map<String, Object> requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);
        var response = RestAssured.given().contentType(ContentType.JSON).body(requestBody)
                .headers(requestHeader)
                .when().post("/api/v1/notifications/subscription/subscribe");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode() );
    }
    @Order(2)
    @Test
    void testUnsubscribe_whenEnteredValidStockAndSubscriptionType_shouldReturnOk200statusCode() throws JSONException, JsonProcessingException {
        // request to auth-service to get jwt
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        String access_token = new JSONObject(authResponse.asString()).getString("access_token");
        //post request to notification-service
        RestAssured.port = port;
        Map<String,Object> requestBody = new HashMap<String,Object>();
        requestBody.put("reutersCode","JUFO");
        requestBody.put("subscriptionType","stock");
        Map<String, Object> requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);
        var response = RestAssured.given().contentType(ContentType.JSON).body(requestBody)
                .headers(requestHeader)
                .when().post("/api/v1/notifications/subscription/unsubscribe");
        assertEquals(HttpStatus.OK.value(), response.getStatusCode() );
    }
    @Test
    void testUnsubscribe_whenNonExistedStockEntered_shouldThrowResourceNotFoundExceptionAnd404statusCode() throws JSONException, JsonProcessingException {
        // request to auth-service to get jwt
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        String access_token = new JSONObject(authResponse.asString()).getString("access_token");
        //post request to notification-service
        RestAssured.port = port;
        Map<String,Object> requestBody = new HashMap<String,Object>();
        requestBody.put("reutersCode","NotExisted");
        requestBody.put("subscriptionType","stock");
        Map<String, Object> requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);
        var response = RestAssured.given().contentType(ContentType.JSON).body(requestBody)
                .headers(requestHeader)
                .when().post("/api/v1/notifications/subscription/unsubscribe");
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode() );
    }
    @Test
    void testUnsubscribe_whenWrongSubscriptionTypeEntered_shouldThrowIllegalArgumentExceptionAnd400statusCode() throws JSONException, JsonProcessingException {
        // request to auth-service to get jwt
        RestAssured.port = 8200;
        Map<String, Object> authRequestBody = new HashMap<>();
        authRequestBody.put("username", "test-notification");
        authRequestBody.put("password", "password");
        var authResponse = RestAssured.given()
                .contentType(ContentType.JSON).body(authRequestBody)
                .when()
                .post("/api/v1/auth/signin");
        assertEquals(HttpStatus.OK.value(), authResponse.getStatusCode());
        String access_token = new JSONObject(authResponse.asString()).getString("access_token");
        //post request to notification-service
        RestAssured.port = port;
        Map<String,Object> requestBody = new HashMap<String,Object>();
        requestBody.put("reutersCode","JUFO");
        requestBody.put("subscriptionType","NotExisted");
        Map<String, Object> requestHeader = new HashMap<String,Object>();
        requestHeader.put("Authorization", "Bearer " + access_token);
        var response = RestAssured.given().contentType(ContentType.JSON).body(requestBody)
                .headers(requestHeader)
                .when().post("/api/v1/notifications/subscription/unsubscribe");
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCode() );
    }
    @Test
    void testUnsubscribe_whenUnauthorizedUser_shouldReturnUnauthorized401statusCode() throws JsonProcessingException {
        var request = RestAssured.given();
        var ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String requestBody = ow.writeValueAsString(new SubscriptionRequest("JUFO","stock"));
        request.body(requestBody);
        var response = RestAssured.post("/api/v1/notifications/subscription/unsubscribe");
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }
}