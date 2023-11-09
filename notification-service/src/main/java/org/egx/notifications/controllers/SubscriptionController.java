package org.egx.notifications.controllers;

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
public class SubscriptionController {

    private final Map<String, SubscriptionService> servicesMap;

    public SubscriptionController(Map<String, SubscriptionService> servicesMap) {
        this.servicesMap = servicesMap;
    }

    @PostMapping("/subscribe")
    public void subscribe(@AuthenticationPrincipal Jwt jwt, @RequestBody SubscriptionRequest subscriptionRequest) throws IllegalAccessException {
        String userEmail = String.valueOf(jwt.getClaims().get("email"));

        Optional.ofNullable(servicesMap.get(SubscriptionServiceImplEnum.getSubscriptionServiceName(subscriptionRequest.getSubscriptionType())))
                .orElseThrow(()-> new IllegalArgumentException("No subscription type found for: "+
                        subscriptionRequest.getSubscriptionType() +" choose between 'stock' or 'news'"))
                .subscribe(userEmail, subscriptionRequest);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribe(@AuthenticationPrincipal Jwt jwt, @RequestBody SubscriptionRequest subscriptionRequest){
        String userEmail = String.valueOf(jwt.getClaims().get("email"));

        Optional.ofNullable(servicesMap.get(SubscriptionServiceImplEnum.getSubscriptionServiceName(subscriptionRequest.getSubscriptionType())))
                .orElseThrow(()-> new IllegalArgumentException("No subscription type found for: "+
                        subscriptionRequest.getSubscriptionType() +" choose between 'stock' or 'news'"))
                .removeSubscription(userEmail, subscriptionRequest);
    }
}
