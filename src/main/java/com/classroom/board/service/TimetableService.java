package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.TimetableDto;
import com.classroom.board.entity.TimetableEntry;
import com.classroom.board.repository.TimetableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimetableService {

    private final TimetableRepository timetableRepository;

    @Transactional(readOnly = true)
    public List<TimetableDto> getTeacherTimetableForToday(Long teacherId) {
        String dayOfWeek = LocalDate.now().getDayOfWeek().name();
        return timetableRepository.findByTeacherIdAndDayOfWeekOrderByPeriodNumberAsc(teacherId, dayOfWeek)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TimetableDto> getTeacherTimetableByDay(Long teacherId, String dayOfWeek) {
        return timetableRepository.findByTeacherIdAndDayOfWeekOrderByPeriodNumberAsc(teacherId, dayOfWeek.toUpperCase())
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TimetableDto> getClassTimetableByDay(Long classId, String dayOfWeek) {
        return timetableRepository.findBySchoolClassIdAndDayOfWeekOrderByPeriodNumberAsc(classId, dayOfWeek.toUpperCase())
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public TimetableDto mapToDto(TimetableEntry t) {
        return TimetableDto.builder()
                .id(t.getId())
                .classId(t.getSchoolClass().getId())
                .className(t.getSchoolClass().getGrade() + "-" + t.getSchoolClass().getSection())
                .subjectId(t.getSubject().getId())
                .subjectName(t.getSubject().getName())
                .teacherId(t.getTeacher().getId())
                .teacherName(t.getTeacher().getUser().getFirstName() + " " + t.getTeacher().getUser().getLastName())
                .classroomId(t.getClassroom().getId())
                .roomNumber(t.getClassroom().getRoomNumber())
                .dayOfWeek(t.getDayOfWeek())
                .startTime(t.getStartTime())
                .endTime(t.getEndTime())
                .periodNumber(t.getPeriodNumber())
                .academicYear(t.getAcademicYear())
                .build();
    }
}
