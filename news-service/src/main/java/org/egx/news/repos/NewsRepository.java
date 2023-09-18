package org.egx.news.repos;

import org.egx.news.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Integer> {

    String appliedFiltersQuery = "SELECT n.* FROM news n" +
            " INNER JOIN equity e on n.equity_id= e.id" +
            " WHERE LOWER(e.sector) LIKE %LOWER(?1)%;" +
            "and LOWER(e.name) LIKE %LOWER(?2)%" +
            "and LOWER(e.reuters_code) LIKE %LOWER(?3)% " +
            "ORDER BY n.news_date DESC, n.news_time DESC;";
    @Query(appliedFiltersQuery)
    Page<News> findAllByFilters(String equityCategoryFilter, String equityNameFilter, String equityReutersFilter, Pageable pageable);

    @Query(appliedFiltersQuery)
    List<News> findAllByFilters(String equityCategoryFilter, String equityNameFilter, String equityReutersFilter);
}