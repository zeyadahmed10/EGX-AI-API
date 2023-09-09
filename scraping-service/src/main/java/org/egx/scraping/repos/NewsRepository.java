package org.egx.scraping.repos;

import org.egx.scraping.IO.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Integer> {
    boolean existsByTitle(String title);
}