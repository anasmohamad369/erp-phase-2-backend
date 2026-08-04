package com.classroom.board.repository;

import com.classroom.board.entity.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChapterRepository extends JpaRepository<Chapter, Long> {
    List<Chapter> findBySubjectIdAndSchoolClassIdOrderByChapterNumberAsc(Long subjectId, Long classId);
}
