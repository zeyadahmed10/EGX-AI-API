package org.egx.news.repos;

import jakarta.transaction.Transactional;
import org.egx.news.entity.Equity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EquityRepository extends JpaRepository<Equity, Integer> {

    String appliedFiltersQuery = "SELECT e.* FROM Equity e" +
            " WHERE LOWER(e.sector) LIKE CONCAT('%',LOWER(:sectorParam),'%')" +
            " and LOWER(e.name) LIKE CONCAT('%',LOWER(:nameParam),'%') ;" ;
    @Query(value = appliedFiltersQuery, nativeQuery = true)
    Page<Equity> findAllByFilters(@Param("sectorParam") String equitySectorFilter,
                                            @Param("nameParam")String equityNameFilter,
                                            Pageable pageable);
    Optional<Equity> findByReutersCode(String code);
   @Transactional
    void deleteByReutersCode(String code);

}