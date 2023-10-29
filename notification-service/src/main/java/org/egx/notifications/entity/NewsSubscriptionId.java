package org.egx.notifications.entity;

import jakarta.persistence.Embeddable;

@Embeddable
public
class NewsSubscriptionId {
    Integer equityId;
    Integer userId;
}
