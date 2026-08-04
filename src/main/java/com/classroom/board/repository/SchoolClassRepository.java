package com.classroom.board.repository;

import com.classroom.board.entity.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, Long> {
    Optional<SchoolClass> findByGradeAndSectionAndAcademicYear(String grade, String section, String academicYear);
    List<SchoolClass> findByClassTeacherId(Long teacherId);
}
