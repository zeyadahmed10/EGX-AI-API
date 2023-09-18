package org.egx.news.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
