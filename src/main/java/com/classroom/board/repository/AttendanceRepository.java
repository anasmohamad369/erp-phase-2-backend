package com.classroom.board.repository;

import com.classroom.board.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findBySchoolClassIdAndDate(Long classId, LocalDate date);
    Optional<Attendance> findByStudentIdAndDate(Long studentId, LocalDate date);
    long countBySchoolClassIdAndDateAndStatus(Long classId, LocalDate date, com.classroom.board.common.enums.AttendanceStatus status);
}
