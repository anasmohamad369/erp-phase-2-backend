package com.classroom.board.service;

import com.classroom.board.common.enums.SubstitutionStatus;
import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.SubstitutionDto;
import com.classroom.board.entity.*;
import com.classroom.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubstitutionService {

    private final SubstitutionRepository substitutionRepository;
    private final TeacherRepository teacherRepository;
    private final TimetableRepository timetableRepository;

    @Transactional
    public SubstitutionDto assignSubstitution(Long timetableId, Long originalTeacherId, Long substituteTeacherId, LocalDate date) {
        TimetableEntry timetable = timetableRepository.findById(timetableId)
                .orElseThrow(() -> new ResourceNotFoundException("TimetableEntry", "id", timetableId));
        Teacher originalTeacher = teacherRepository.findById(originalTeacherId)
                .orElseThrow(() -> new ResourceNotFoundException("OriginalTeacher", "id", originalTeacherId));
        Teacher substituteTeacher = teacherRepository.findById(substituteTeacherId)
                .orElseThrow(() -> new ResourceNotFoundException("SubstituteTeacher", "id", substituteTeacherId));

        Substitution substitution = Substitution.builder()
                .timetableEntry(timetable)
                .originalTeacher(originalTeacher)
                .substituteTeacher(substituteTeacher)
                .substitutionDate(date)
                .status(SubstitutionStatus.ASSIGNED)
                .build();

        Substitution saved = substitutionRepository.save(substitution);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SubstitutionDto> getSubstitutionsForTeacherToday(Long substituteTeacherId) {
        return substitutionRepository.findBySubstituteTeacherIdAndSubstitutionDate(substituteTeacherId, LocalDate.now())
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    public SubstitutionDto mapToDto(Substitution s) {
        return SubstitutionDto.builder()
                .id(s.getId())
                .leaveRequestId(s.getLeaveRequest() != null ? s.getLeaveRequest().getId() : null)
                .timetableId(s.getTimetableEntry().getId())
                .originalTeacherId(s.getOriginalTeacher().getId())
                .originalTeacherName(s.getOriginalTeacher().getUser().getFirstName() + " " + s.getOriginalTeacher().getUser().getLastName())
                .substituteTeacherId(s.getSubstituteTeacher().getId())
                .substituteTeacherName(s.getSubstituteTeacher().getUser().getFirstName() + " " + s.getSubstituteTeacher().getUser().getLastName())
                .className(s.getTimetableEntry().getSchoolClass().getGrade() + "-" + s.getTimetableEntry().getSchoolClass().getSection())
                .subjectName(s.getTimetableEntry().getSubject().getName())
                .periodNumber(s.getTimetableEntry().getPeriodNumber())
                .substitutionDate(s.getSubstitutionDate())
                .status(s.getStatus())
                .build();
    }
}
