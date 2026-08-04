package com.classroom.board.repository;

import com.classroom.board.entity.TeacherDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TeacherDiaryRepository extends JpaRepository<TeacherDiary, Long> {
    List<TeacherDiary> findByTeacherIdAndEntryDate(Long teacherId, LocalDate entryDate);
    List<TeacherDiary> findBySchoolClassIdAndEntryDate(Long classId, LocalDate entryDate);
}
