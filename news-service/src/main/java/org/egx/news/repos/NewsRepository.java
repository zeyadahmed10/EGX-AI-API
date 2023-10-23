package org.egx.news.repos;

import org.egx.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NewsRepository extends JpaRepository<News, Integer> {

    String appliedFiltersQuery = "SELECT n.* FROM news n" +
            " INNER JOIN equity e on n.equity_id= e.id" +
            " WHERE LOWER(e.sector) LIKE CONCAT('%',LOWER(:categoryParam),'%')" +
            " and LOWER(e.name) LIKE CONCAT('%',LOWER(:nameParam),'%')" +
            " and LOWER(e.reuters_code) LIKE CONCAT('%',LOWER(:codeParam),'%')" +
            " ORDER BY n.time DESC;";
    @Query(value = appliedFiltersQuery, nativeQuery = true)
    Page<News> findAllByFilters(@Param("categoryParam") String equityCategoryFilter,
                                          @Param("nameParam")String equityNameFilter,
                                          @Param("codeParam")String equityReutersFilter, Pageable pageable);

//    @Query(value = appliedFiltersQuery, nativeQuery = true)
//    List<News> findAllByFilters(String equityCategoryFilter, String equityNameFilter, String equityReutersFilter);
}