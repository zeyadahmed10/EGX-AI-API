package org.egx.notifications.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public
class StockSubscriptionId {
    Integer equityId;
    Integer userId;
}
