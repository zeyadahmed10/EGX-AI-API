package org.egx.notifications.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EmbeddedId
    SubscriptionId id;
}
