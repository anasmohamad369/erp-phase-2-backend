package com.classroom.board.repository;

import com.classroom.board.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByUserId(Long userId);
    Optional<Teacher> findByUserUsername(String username);
    Optional<Teacher> findByEmployeeId(String employeeId);
    Boolean existsByEmployeeId(String employeeId);
}
