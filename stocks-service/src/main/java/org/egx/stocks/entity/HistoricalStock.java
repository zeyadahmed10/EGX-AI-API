package org.egx.stocks.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "historical_stock")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class HistoricalStock extends Stock{
    @Builder
    public HistoricalStock(Integer id, String reutersCode, double currPrice, double rateOfChange, double percentageOfChange, double open, double prevClose, double highest, double lowest, double volume, double value, Timestamp time, Equity equity) {
        super(id, reutersCode, currPrice, rateOfChange, percentageOfChange, open, prevClose, highest, lowest, volume, value, time);
        this.equity = equity;
    }
    @ManyToOne
    @JoinColumn(name ="equity_id")
    private Equity equity;
}
