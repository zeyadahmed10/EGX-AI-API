package org.egx.notifications.repos;

import org.egx.notifications.entity.StockSubscription;
import org.egx.notifications.entity.StockSubscriptionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockSubscriptionRepository extends JpaRepository<StockSubscription, StockSubscriptionId> {
}