package org.egx.notifications.controllers;

import org.egx.notifications.dto.SubscriptionRequest;
import org.egx.notifications.services.NewsSubscriptionService;
import org.egx.notifications.services.StockSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/notifications/subscription")
public class SubscriptionController {

    @Autowired
    private StockSubscriptionService stockSubscriptionService;
    @Autowired
    private NewsSubscriptionService newsSubscriptionService;
    @PostMapping
    public void subscribe(@AuthenticationPrincipal Jwt jwt, @RequestBody SubscriptionRequest subscriptionRequest) throws IllegalAccessException {
        String userEmail = String.valueOf(jwt.getClaims().get("email"));

        //subscriptionService.subscribe(userEmail, subscriptionRequest);
    }

    @PostMapping
    public void unsubscribe(@AuthenticationPrincipal Jwt jwt, @RequestBody SubscriptionRequest subscriptionRequest){

    }
}
