package org.egx.notifications.repos;

import org.egx.notifications.entity.SubscribedUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscribedUserRepository extends JpaRepository<SubscribedUser, Integer> {

    Optional<SubscribedUser> findByEmail(String email);
}