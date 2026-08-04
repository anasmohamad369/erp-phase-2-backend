package com.classroom.board.repository;

import com.classroom.board.entity.Recording;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordingRepository extends JpaRepository<Recording, Long> {
    List<Recording> findBySchoolClassIdAndSubjectId(Long classId, Long subjectId);
    List<Recording> findByTeacherId(Long teacherId);
}
