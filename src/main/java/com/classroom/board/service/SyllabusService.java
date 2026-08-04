package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.SyllabusProgressDto;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.SyllabusProgress;
import com.classroom.board.entity.Teacher;
import com.classroom.board.entity.Topic;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.SyllabusProgressRepository;
import com.classroom.board.repository.TeacherRepository;
import com.classroom.board.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyllabusService {

    private final SyllabusProgressRepository syllabusProgressRepository;
    private final TopicRepository topicRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public SyllabusProgressDto markTopicCompletion(Long topicId, Long classId, Long teacherId, boolean completed, String remarks) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", topicId));
        SchoolClass schoolClass = schoolClassRepository.findById(classId)
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", classId));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", teacherId));

        SyllabusProgress progress = syllabusProgressRepository.findByTopicIdAndSchoolClassId(topicId, classId)
                .orElseGet(() -> SyllabusProgress.builder()
                        .topic(topic)
                        .schoolClass(schoolClass)
                        .teacher(teacher)
                        .build());

        progress.setIsCompleted(completed);
        progress.setCompletionDate(completed ? LocalDate.now() : null);
        progress.setRemarks(remarks);
        progress.setTeacher(teacher);

        SyllabusProgress saved = syllabusProgressRepository.save(progress);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<SyllabusProgressDto> getSyllabusProgress(Long classId, Long subjectId) {
        return syllabusProgressRepository.findBySchoolClassIdAndTopicChapterSubjectId(classId, subjectId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public SyllabusProgressDto mapToDto(SyllabusProgress p) {
        return SyllabusProgressDto.builder()
                .id(p.getId())
                .topicId(p.getTopic().getId())
                .topicTitle(p.getTopic().getTitle())
                .topicNumber(p.getTopic().getTopicNumber())
                .chapterId(p.getTopic().getChapter().getId())
                .chapterTitle(p.getTopic().getChapter().getTitle())
                .classId(p.getSchoolClass().getId())
                .teacherId(p.getTeacher().getId())
                .isCompleted(p.getIsCompleted())
                .completionDate(p.getCompletionDate())
                .remarks(p.getRemarks())
                .build();
    }
}
