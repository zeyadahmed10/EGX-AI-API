package org.egx.recommendation.repos;

import org.egx.recommendation.entity.UserHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserHistoryRepository extends JpaRepository<UserHistory, Integer> {
    boolean existsByEmail(String email);
    Optional<UserHistory> findByEmail(String email);
}