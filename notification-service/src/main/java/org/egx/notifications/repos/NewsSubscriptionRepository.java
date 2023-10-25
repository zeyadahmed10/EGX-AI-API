package org.egx.notifications.repos;

import org.egx.notifications.entity.NewsSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsSubscriptionRepository extends MongoRepository<NewsSubscription, String> {
}
