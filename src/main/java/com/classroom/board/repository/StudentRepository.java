package com.classroom.board.repository;

import com.classroom.board.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findBySchoolClassIdOrderByRollNumberAsc(Long classId);
    Optional<Student> findByAdmissionNumber(String admissionNumber);
    long countBySchoolClassId(Long classId);
}
