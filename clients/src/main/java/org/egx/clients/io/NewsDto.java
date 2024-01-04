package org.egx.clients.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDto {
    private Integer id;
    private String title;
    private String article;
    private Timestamp time;
    private String reutersCode;
    private String sector;
    private String name;
}
