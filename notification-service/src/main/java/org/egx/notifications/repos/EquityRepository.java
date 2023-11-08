package org.egx.notifications.repos;

import org.egx.notifications.entity.Equity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquityRepository extends JpaRepository<Equity, Integer> {

    Optional<Equity> findByReutersCode(String code);
}