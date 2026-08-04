package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.HomeworkDto;
import com.classroom.board.entity.FileStorage;
import com.classroom.board.entity.Homework;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.Subject;
import com.classroom.board.entity.Teacher;
import com.classroom.board.repository.FileStorageRepository;
import com.classroom.board.repository.HomeworkRepository;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.SubjectRepository;
import com.classroom.board.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeworkService {

    private final HomeworkRepository homeworkRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final FileStorageRepository fileStorageRepository;

    @Transactional
    public HomeworkDto createHomework(HomeworkDto dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", dto.getTeacherId()));
        SchoolClass schoolClass = schoolClassRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", dto.getClassId()));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", dto.getSubjectId()));

        FileStorage file = null;
        if (dto.getFileId() != null) {
            file = fileStorageRepository.findById(dto.getFileId())
                    .orElseThrow(() -> new ResourceNotFoundException("File", "id", dto.getFileId()));
        }

        Homework homework = Homework.builder()
                .title(dto.getTitle())
                .instructions(dto.getInstructions())
                .schoolClass(schoolClass)
                .subject(subject)
                .teacher(teacher)
                .file(file)
                .assignedDate(dto.getAssignedDate() != null ? dto.getAssignedDate() : LocalDate.now())
                .dueDate(dto.getDueDate())
                .build();

        Homework saved = homeworkRepository.save(homework);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<HomeworkDto> getHomeworkForClassAndSubject(Long classId, Long subjectId) {
        return homeworkRepository.findBySchoolClassIdAndSubjectId(classId, subjectId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public HomeworkDto mapToDto(Homework h) {
        return HomeworkDto.builder()
                .id(h.getId())
                .title(h.getTitle())
                .instructions(h.getInstructions())
                .classId(h.getSchoolClass().getId())
                .subjectId(h.getSubject().getId())
                .teacherId(h.getTeacher().getId())
                .fileId(h.getFile() != null ? h.getFile().getId() : null)
                .fileName(h.getFile() != null ? h.getFile().getFileName() : null)
                .fileDownloadUrl(h.getFile() != null ? "/api/v1/files/download/" + h.getFile().getId() : null)
                .assignedDate(h.getAssignedDate())
                .dueDate(h.getDueDate())
                .build();
    }
}
