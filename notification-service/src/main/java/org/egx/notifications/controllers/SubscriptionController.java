package org.egx.notifications.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.egx.notifications.dto.SubscriptionRequest;
import org.egx.notifications.enums.SubscriptionServiceImplEnum;
import org.egx.notifications.services.SubscriptionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/notifications/subscription")
@Tag(name = "Subscription Controller", description = "APIs for managing subscriptions and notifications")
public class SubscriptionController {

    private final Map<String, SubscriptionService> servicesMap;

    public SubscriptionController(Map<String, SubscriptionService> servicesMap) {
        this.servicesMap = servicesMap;
    }

    @Operation(
            summary = "Subscribe to news or stock notifications",
            description = "Subscribe to email notifications for news or stock updates based on the subscribed equity."
    )
    @PostMapping("/subscribe")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Equity not found")
    @ApiResponse(responseCode = "404", description = "No subscription found, choose between 'stock' or 'news' subscription")
    public void subscribe(
            @Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt,
            @RequestBody SubscriptionRequest subscriptionRequest
    ) throws IllegalAccessException {
        String userEmail = String.valueOf(jwt.getClaims().get("email"));
        String name = String.valueOf(jwt.getClaims().get("name"));
        Optional.ofNullable(servicesMap.get(SubscriptionServiceImplEnum.getSubscriptionServiceName(subscriptionRequest.getSubscriptionType())))
                .orElseThrow(()-> new IllegalArgumentException("No subscription type found for: "+
                        subscriptionRequest.getSubscriptionType() +" choose between 'stock' or 'news'"))
                .subscribe(userEmail, name, subscriptionRequest);
    }

    @Operation(
            summary = "Unsubscribe from news or stock notifications",
            description = "Unsubscribe from email notifications for news or stock updates based on the subscribed equity."
    )
    @PostMapping("/unsubscribe")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "404", description = "Equity not found")
    @ApiResponse(responseCode = "404", description = "No subscription found, choose between 'stock' or 'news' subscription")
    public void unsubscribe(@Parameter(hidden = true) @AuthenticationPrincipal Jwt jwt, @RequestBody SubscriptionRequest subscriptionRequest){
        String userEmail = String.valueOf(jwt.getClaims().get("email"));

        Optional.ofNullable(servicesMap.get(SubscriptionServiceImplEnum.getSubscriptionServiceName(subscriptionRequest.getSubscriptionType())))
                .orElseThrow(()-> new IllegalArgumentException("No subscription type found for: "+
                        subscriptionRequest.getSubscriptionType() +" choose between 'stock' or 'news'"))
                .removeSubscription(userEmail, subscriptionRequest);
    }
}
