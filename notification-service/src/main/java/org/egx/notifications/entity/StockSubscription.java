package org.egx.notifications.entity;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class StockSubscription extends Subscription {
    @Builder
    public StockSubscription(SubscriptionId id) {
        super(id);
    }
    public StockSubscription() {
    }
}
