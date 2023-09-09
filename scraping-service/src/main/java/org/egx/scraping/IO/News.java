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
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Integer Id;
    String reutersCode;
    String title;
    @Transient
    String article;
    String date;
    String time;

    News(String title, String article) {
        this.reutersCode = reutersCode;
        this.title = title;
        this.article = article;
        this.date = new SimpleDateFormat("yyyy/MM/dd").format(Calendar.getInstance().getTime());
        this.time = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
    }

}
