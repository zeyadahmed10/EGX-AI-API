package org.egx.stocks.repos;

import org.egx.stocks.entity.UpdatedStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UpdatedStockRepository extends JpaRepository<UpdatedStock, Integer> {
    String appliedFiltersQuery = "SELECT us.* FROM updated_stock us" +
            " INNER JOIN equity e on us.equity_id= e.id" +
            " WHERE LOWER(e.sector) LIKE CONCAT('%',LOWER(:sectorParam),'%')" +
            " and LOWER(e.name) LIKE CONCAT('%',LOWER(:nameParam),'%')" +
            " ORDER BY us.percentage_of_change DESC";
    @Query(value = appliedFiltersQuery, nativeQuery = true)
    Page<UpdatedStock> findAllStocksByFilters(@Param("sectorParam") String sectorFilter,
                                              @Param("nameParam")String nameFilter,
                                              Pageable pageRequest);
    Optional<UpdatedStock> findByEquity_reutersCode(String reutersCode);
}