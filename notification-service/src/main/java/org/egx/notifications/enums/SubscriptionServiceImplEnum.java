package org.egx.notifications.enums;

import lombok.Getter;

@Getter

public enum SubscriptionServiceImplEnum {
    NEWS("news","newSubscriptionService"),
    STOCK("stock","stockSubscriptionService");
    private final String key;
    private final String value;

    SubscriptionServiceImplEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getSubscriptionServiceName(String key){
        for(var e: SubscriptionServiceImplEnum.values()){
            if(e.getKey().equalsIgnoreCase(key))
                return e.getValue();
        }
        return key;
    }

}