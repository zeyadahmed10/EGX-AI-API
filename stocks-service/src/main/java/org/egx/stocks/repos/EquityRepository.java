package org.egx.stocks.repos;

import org.egx.stocks.entity.Equity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquityRepository  extends JpaRepository<Equity, Integer> {
    Optional<Equity> findByReutersCode(String reutersCode);
}
