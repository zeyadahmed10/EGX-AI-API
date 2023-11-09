package org.egx.notifications.repos;

import org.egx.notifications.entity.NewsSubscription;
import org.egx.notifications.entity.SubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("NewsSubscriptionRepository")
public interface NewsSubscriptionRepository extends JpaRepository<NewsSubscription, SubscriptionId> {
}