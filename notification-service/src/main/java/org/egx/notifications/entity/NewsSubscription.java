package org.egx.notifications.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
class NewsSubscriptionId { Integer equityId; Integer userId; }
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class NewsSubscription {
    @EmbeddedId
    private NewsSubscriptionId id;
}
