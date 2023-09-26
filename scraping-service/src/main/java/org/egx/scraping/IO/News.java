package org.egx.scraping.IO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Data
@NoArgsConstructor
@Entity
@Table(name ="news",indexes = @Index(columnList = "title"))
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;
    @Column(nullable = false)
    String reutersCode;
    @Column(nullable = false)
    String title;
    @Transient
    String article;
    Timestamp time;

    public News(String reutersCode, String title, String article) {
        this.reutersCode = reutersCode;
        this.title = title;
        this.article = article;
        try {
            String stringTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .format(Calendar.getInstance().getTime());
            this.time = Timestamp.valueOf(stringTime);
        } catch(Exception e) { //this generic but you can control another types of exception
            throw new IllegalStateException("Invalid time format: " +e.getMessage());
        }
    }

}
