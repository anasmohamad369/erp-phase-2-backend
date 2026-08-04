package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.WorksheetDto;
import com.classroom.board.entity.FileStorage;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.Subject;
import com.classroom.board.entity.Teacher;
import com.classroom.board.entity.Worksheet;
import com.classroom.board.repository.FileStorageRepository;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.SubjectRepository;
import com.classroom.board.repository.TeacherRepository;
import com.classroom.board.repository.WorksheetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorksheetService {

    private final WorksheetRepository worksheetRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final FileStorageRepository fileStorageRepository;

    @Transactional
    public WorksheetDto createWorksheet(WorksheetDto dto) {
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

        Worksheet worksheet = Worksheet.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .schoolClass(schoolClass)
                .subject(subject)
                .teacher(teacher)
                .file(file)
                .dueDate(dto.getDueDate())
                .build();

        Worksheet saved = worksheetRepository.save(worksheet);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<WorksheetDto> getWorksheetsForClassAndSubject(Long classId, Long subjectId) {
        return worksheetRepository.findBySchoolClassIdAndSubjectId(classId, subjectId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public WorksheetDto mapToDto(Worksheet w) {
        return WorksheetDto.builder()
                .id(w.getId())
                .title(w.getTitle())
                .description(w.getDescription())
                .classId(w.getSchoolClass().getId())
                .subjectId(w.getSubject().getId())
                .teacherId(w.getTeacher().getId())
                .fileId(w.getFile() != null ? w.getFile().getId() : null)
                .fileName(w.getFile() != null ? w.getFile().getFileName() : null)
                .fileDownloadUrl(w.getFile() != null ? "/api/v1/files/download/" + w.getFile().getId() : null)
                .dueDate(w.getDueDate())
                .build();
    }
}
