package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyllabusProgressDto {
    private Long id;
    private Long topicId;
    private String topicTitle;
    private Integer topicNumber;
    private Long chapterId;
    private String chapterTitle;
    private Long classId;
    private Long teacherId;
    private Boolean isCompleted;
    private LocalDate completionDate;
    private String remarks;
}
