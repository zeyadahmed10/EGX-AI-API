package org.egx.news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String title;
    private String article;
    private String newsData;
    private String newsTime;
    @ManyToOne
    private Category category;
}
