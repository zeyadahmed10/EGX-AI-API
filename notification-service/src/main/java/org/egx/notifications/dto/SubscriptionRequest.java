package org.egx.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
enum SubscriptionType{
    NEWS("news"),
    STOCK("stock");
    public final String type;
    SubscriptionType(String type) {
        this.type = type;
    }
}
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionRequest {

    private String reutersCode;
    private String subscriptionType;
}
