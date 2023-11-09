package org.egx.notifications.repos;

import org.egx.notifications.entity.SubscribedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubscribedUserRepository extends JpaRepository<SubscribedUser, Integer> {
    String GET_SUB_USERS_QUERY = "select su.* from equity e \n" +
            "join stock_subscription as ss\n" +
            "on ss.equity_id = e.id\n" +
            "join subscribed_user as su\n" +
            "on su.id=ss.user_id\n" +
            "where e.reuters_code = :codeParam ;";
    Optional<SubscribedUser> findByEmail(String email);
    @Query(value = GET_SUB_USERS_QUERY, nativeQuery = true)
    List<SubscribedUser> findStockSubscribedUsersByReutersCode(@Param("codeParam") String reutersCode);
}