package org.egx.notifications.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.egx.clients.io.BaseEquity;

@Entity
@Table(name = "equity")
@AllArgsConstructor
@Setter
@Getter
public class Equity extends BaseEquity {

    @Builder
    public Equity(Integer id, String reutersCode, String name, String ISN, String sector, String listingDate) {
        super(id, reutersCode, name, ISN, sector, listingDate);
    }
}
