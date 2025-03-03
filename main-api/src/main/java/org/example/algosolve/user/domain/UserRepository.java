package org.example.algosolve.user.domain;

import org.example.algosolve.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserId(String userId);

    boolean existsByUserId(String userId);

    Optional<User> findUserByUserIdAndRefreshToken(String id, String refreshToken);
}
