package org.egx.news.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.egx.clients.io.BaseNews;

import java.sql.Timestamp;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class News extends BaseNews {

    @Builder
    public News(Integer id, String title, String article, Timestamp time, Equity equity) {
        super(id, title, article, time);
        this.equity = equity;
    }

    @ManyToOne
    @JoinColumn(name = "equity_id")
    private Equity equity;
}
