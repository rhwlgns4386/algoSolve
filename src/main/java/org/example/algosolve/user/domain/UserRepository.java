package org.example.algosolve.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByUserId(String userId);

    boolean existsByUserId(String userId);
}
