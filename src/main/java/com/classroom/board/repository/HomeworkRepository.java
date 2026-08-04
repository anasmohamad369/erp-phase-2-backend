package com.classroom.board.repository;

import com.classroom.board.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    List<Homework> findBySchoolClassIdAndSubjectId(Long classId, Long subjectId);
    List<Homework> findByTeacherId(Long teacherId);
}
