package com.classroom.board.repository;

import com.classroom.board.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByChapterIdOrderByTopicNumberAsc(Long chapterId);
}
