package org.egx.notifications.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
class StockSubscriptionId { Integer equityId; Integer userId; }
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockSubscription {
    @EmbeddedId
    StockSubscriptionId id;
}
