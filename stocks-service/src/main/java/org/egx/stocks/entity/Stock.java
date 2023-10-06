package org.egx.stocks.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(nullable = false)
    private double currPrice;
    @Column(nullable = false)
    private double rateOfChange;
    @Column(nullable = false)
    private double percentageOfChange;
    @Column(nullable = false)
    private double open;
    @Column(nullable = false)
    private double prevClose;
    @Column(nullable = false)
    private double highest;
    @Column(nullable = false)
    private double lowest;
    @Column(nullable = false)
    private double volume;
    @Column(nullable = false)
    private double value;
    @Column(nullable = false)
    private Timestamp time;

}
