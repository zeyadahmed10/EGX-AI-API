package org.egx.clients.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ScrapedNews {
    String reutersCode;
    String title;
    String article;
    String date;
    String time;
}
