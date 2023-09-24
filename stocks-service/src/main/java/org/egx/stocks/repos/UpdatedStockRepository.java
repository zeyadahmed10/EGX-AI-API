package org.egx.stocks.repos;

import org.egx.stocks.entity.UpdatedStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpdatedStockRepository extends JpaRepository<UpdatedStock, Integer> {

    Optional<UpdatedStock> findByEquity_reutersCode(String reutersCode);
}