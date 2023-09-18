package org.egx.news.repos;

import org.egx.news.entity.Equity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquityRepository extends JpaRepository<Equity, Integer> {
}