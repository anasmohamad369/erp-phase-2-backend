package com.classroom.board.repository;

import com.classroom.board.entity.TimetableEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimetableRepository extends JpaRepository<TimetableEntry, Long> {
    List<TimetableEntry> findByTeacherIdAndDayOfWeekOrderByPeriodNumberAsc(Long teacherId, String dayOfWeek);
    List<TimetableEntry> findBySchoolClassIdAndDayOfWeekOrderByPeriodNumberAsc(Long classId, String dayOfWeek);
    List<TimetableEntry> findByClassroomIdAndDayOfWeekOrderByPeriodNumberAsc(Long classroomId, String dayOfWeek);
}
