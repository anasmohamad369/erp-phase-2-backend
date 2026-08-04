package com.classroom.board.repository;

import com.classroom.board.entity.Worksheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorksheetRepository extends JpaRepository<Worksheet, Long> {
    List<Worksheet> findBySchoolClassIdAndSubjectId(Long classId, Long subjectId);
    List<Worksheet> findByTeacherId(Long teacherId);
}
