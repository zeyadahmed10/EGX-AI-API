package org.egx.notifications.repos;

import org.egx.notifications.entity.Equity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquityRepository extends JpaRepository<Equity, Integer> {
}