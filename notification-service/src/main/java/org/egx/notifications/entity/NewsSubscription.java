package org.egx.notifications.entity;

import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Setter
@Getter
public class NewsSubscription extends Subscription{

    @Builder
    public NewsSubscription(SubscriptionId id) {
        super(id);
    }

    public NewsSubscription() {
    }
}
