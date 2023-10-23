package org.egx.stocks.repos;

import org.egx.stocks.entity.HistoricalStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricalStockRepository extends JpaRepository<HistoricalStock, Integer> {

    String OHCLVQuery = "select * from get_ohclv_proc(:periodParam, :reutersParam,  :intervalParam);";
    @Query(value = OHCLVQuery, nativeQuery = true)
    Page<HistoricalStock> findOHCLVForEquity(@Param("reutersParam") String reutersParam,
                                             @Param("periodParam")String periodParam,
                                             @Param("intervalParam") String intervalParam,
                                             Pageable pageable);

    boolean existsByEquity_reutersCode(String reutersCode);
}