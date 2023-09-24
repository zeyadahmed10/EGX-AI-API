package org.egx.stocks.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "updated_stock")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdatedStock extends Stock{
    @Builder
    public UpdatedStock(Integer id, String reutersCode, double currPrice, double rateOfChange, double percentageOfChange, double open, double prevClose, double highest, double lowest, double volume, double value, Timestamp time, Equity equity) {
        super(id, reutersCode, currPrice, rateOfChange, percentageOfChange, open, prevClose, highest, lowest, volume, value, time);
        this.equity = equity;
    }
    @OneToOne
    @JoinColumn(name ="equity_id")
    private Equity equity;

}
