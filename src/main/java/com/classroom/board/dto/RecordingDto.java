package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordingDto {
    private Long id;
    private String title;
    private Long classId;
    private String className;
    private Long subjectId;
    private String subjectName;
    private Long teacherId;
    private String teacherName;
    private Long fileId;
    private String fileDownloadUrl;
    private Integer durationSeconds;
    private Instant recordedAt;
}
