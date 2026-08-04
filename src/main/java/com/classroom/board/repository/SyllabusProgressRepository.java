package com.classroom.board.repository;

import com.classroom.board.entity.SyllabusProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SyllabusProgressRepository extends JpaRepository<SyllabusProgress, Long> {
    Optional<SyllabusProgress> findByTopicIdAndSchoolClassId(Long topicId, Long classId);
    List<SyllabusProgress> findBySchoolClassIdAndTopicChapterSubjectId(Long classId, Long subjectId);
    long countBySchoolClassIdAndTopicChapterSubjectIdAndIsCompletedTrue(Long classId, Long subjectId);
}
