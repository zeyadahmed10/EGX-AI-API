package org.egx.notifications.repos;

import org.egx.notifications.entity.NewsSubscription;
import org.egx.notifications.entity.NewsSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsSubscriptionRepository extends JpaRepository<NewsSubscription, NewsSubscriptionId> {
}