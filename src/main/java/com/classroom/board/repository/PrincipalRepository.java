package com.classroom.board.repository;

import com.classroom.board.entity.Principal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrincipalRepository extends JpaRepository<Principal, Long> {
    Optional<Principal> findByUserId(Long userId);
    Optional<Principal> findByUserUsername(String username);
}
