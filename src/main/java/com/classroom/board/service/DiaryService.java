package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.TeacherDiaryDto;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.Subject;
import com.classroom.board.entity.Teacher;
import com.classroom.board.entity.TeacherDiary;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.SubjectRepository;
import com.classroom.board.repository.TeacherDiaryRepository;
import com.classroom.board.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final TeacherDiaryRepository teacherDiaryRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public TeacherDiaryDto createOrUpdateDiary(TeacherDiaryDto dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", dto.getTeacherId()));
        SchoolClass schoolClass = schoolClassRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", dto.getClassId()));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", dto.getSubjectId()));

        TeacherDiary diary;
        if (dto.getId() != null) {
            diary = teacherDiaryRepository.findById(dto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("TeacherDiary", "id", dto.getId()));
        } else {
            diary = new TeacherDiary();
        }

        diary.setTeacher(teacher);
        diary.setSchoolClass(schoolClass);
        diary.setSubject(subject);
        diary.setEntryDate(dto.getEntryDate() != null ? dto.getEntryDate() : LocalDate.now());
        diary.setPeriodNumber(dto.getPeriodNumber());
        diary.setTopicsCovered(dto.getTopicsCovered());
        diary.setTeachingMethodology(dto.getTeachingMethodology());
        diary.setReflectionNotes(dto.getReflectionNotes());
        diary.setBoardStateSummary(dto.getBoardStateSummary());

        TeacherDiary saved = teacherDiaryRepository.save(diary);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<TeacherDiaryDto> getTeacherDiaries(Long teacherId, LocalDate date) {
        return teacherDiaryRepository.findByTeacherIdAndEntryDate(teacherId, date).stream()
                .map(this::mapToDto)
                .toList();
    }

    public TeacherDiaryDto mapToDto(TeacherDiary d) {
        return TeacherDiaryDto.builder()
                .id(d.getId())
                .teacherId(d.getTeacher().getId())
                .classId(d.getSchoolClass().getId())
                .subjectId(d.getSubject().getId())
                .entryDate(d.getEntryDate())
                .periodNumber(d.getPeriodNumber())
                .topicsCovered(d.getTopicsCovered())
                .teachingMethodology(d.getTeachingMethodology())
                .reflectionNotes(d.getReflectionNotes())
                .boardStateSummary(d.getBoardStateSummary())
                .build();
    }
}
