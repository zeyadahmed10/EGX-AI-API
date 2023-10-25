package org.egx.notifications.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
public class NewsSubscription {
    @Id
    private String id;
    private String reutersCode;
    private List<String> subscribedUsers;
}
