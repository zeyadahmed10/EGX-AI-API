package org.egx.notifications.repos;

import org.egx.notifications.entity.StockSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockSubscriptionRepository extends MongoRepository<StockSubscription, String> {
}
