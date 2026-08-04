package com.classroom.board.repository;

import com.classroom.board.entity.TeacherAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherAssignmentRepository extends JpaRepository<TeacherAssignment, Long> {
    List<TeacherAssignment> findByTeacherId(Long teacherId);
    List<TeacherAssignment> findBySchoolClassId(Long classId);
}
