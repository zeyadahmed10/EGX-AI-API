package org.egx.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("StockSubscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockSubscription {
    @Id
    String id;
    String reutersCode;
    List<String> subscribedUsers;
}
