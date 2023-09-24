package org.egx.stocks.repos;

import org.egx.stocks.entity.HistoricalStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalStockRepository extends JpaRepository<HistoricalStock, Integer> {
}