package org.egx.notifications.services;

import org.egx.notifications.dto.SubscriptionRequest;

public interface SubscriptionService {
    void subscribe(String userEmail, String name, SubscriptionRequest subscriptionRequest);
    void removeSubscription(String userEmail, SubscriptionRequest subscriptionRequest);
}
