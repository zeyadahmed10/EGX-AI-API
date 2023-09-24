package org.egx.stocks.repos;

import org.egx.stocks.entity.Equity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquityRepository  extends JpaRepository<Equity, Integer> {
}
