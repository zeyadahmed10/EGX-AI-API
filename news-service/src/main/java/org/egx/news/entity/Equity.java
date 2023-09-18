package org.egx.news.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Equity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    private String reutersCode;
    private String name;
    private String ISN;
    private String sector;
    private String listingDate;
    @OneToMany(mappedBy = "equity")
    private List<News> news;

}
