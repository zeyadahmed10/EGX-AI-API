package org.egx.notifications.repos;

import org.egx.notifications.entity.StockSubscription;
import org.egx.notifications.entity.SubscriptionId;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Primary
@Repository("StockSubscriptionRepository")
public interface StockSubscriptionRepository extends JpaRepository<StockSubscription, SubscriptionId> {
}