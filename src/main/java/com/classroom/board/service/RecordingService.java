package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.RecordingDto;
import com.classroom.board.entity.*;
import com.classroom.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordingService {

    private final RecordingRepository recordingRepository;
    private final TeacherRepository teacherRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final FileStorageRepository fileStorageRepository;

    @Transactional
    public RecordingDto saveRecording(RecordingDto dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", dto.getTeacherId()));
        SchoolClass schoolClass = schoolClassRepository.findById(dto.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", dto.getClassId()));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", dto.getSubjectId()));
        FileStorage file = fileStorageRepository.findById(dto.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "id", dto.getFileId()));

        Recording recording = Recording.builder()
                .title(dto.getTitle())
                .schoolClass(schoolClass)
                .subject(subject)
                .teacher(teacher)
                .file(file)
                .durationSeconds(dto.getDurationSeconds())
                .recordedAt(dto.getRecordedAt() != null ? dto.getRecordedAt() : Instant.now())
                .build();

        Recording saved = recordingRepository.save(recording);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<RecordingDto> getRecordingsForClassAndSubject(Long classId, Long subjectId) {
        return recordingRepository.findBySchoolClassIdAndSubjectId(classId, subjectId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public RecordingDto mapToDto(Recording r) {
        return RecordingDto.builder()
                .id(r.getId())
                .title(r.getTitle())
                .classId(r.getSchoolClass().getId())
                .className(r.getSchoolClass().getGrade() + "-" + r.getSchoolClass().getSection())
                .subjectId(r.getSubject().getId())
                .subjectName(r.getSubject().getName())
                .teacherId(r.getTeacher().getId())
                .teacherName(r.getTeacher().getUser().getFirstName() + " " + r.getTeacher().getUser().getLastName())
                .fileId(r.getFile().getId())
                .fileDownloadUrl("/api/v1/files/download/" + r.getFile().getId())
                .durationSeconds(r.getDurationSeconds())
                .recordedAt(r.getRecordedAt())
                .build();
    }
}
