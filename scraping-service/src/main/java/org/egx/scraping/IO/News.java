package org.egx.scraping.IO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String reutersCode;
    String title;
    @Transient
    String article;
    String newsDate;
    String newsTime;

    public News(String reutersCode, String title, String article) {
        this.reutersCode = reutersCode;
        this.title = title;
        this.article = article;
        this.newsDate = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        this.newsTime = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
