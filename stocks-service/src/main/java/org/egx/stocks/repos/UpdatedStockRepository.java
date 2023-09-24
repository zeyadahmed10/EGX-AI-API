package org.egx.stocks.repos;

import org.egx.stocks.entity.UpdatedStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdatedStockRepository extends JpaRepository<UpdatedStock, Integer> {
}