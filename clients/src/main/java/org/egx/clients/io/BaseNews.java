package org.egx.clients.io;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
public class BaseNews {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(unique = true, nullable = false)
    private String title;
    @Column(columnDefinition="TEXT")
    private String article;
    @Column(nullable = false)
    private Timestamp time;
}
