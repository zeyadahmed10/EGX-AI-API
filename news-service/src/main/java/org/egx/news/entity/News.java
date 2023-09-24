package org.egx.news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String title;
    @Column(columnDefinition="TEXT")
    private String article;
    @Column(nullable = false)
    private Timestamp time;
    @ManyToOne
    @JoinColumn(name = "equity_id")
    private Equity equity;
}
