package com.classroom.board.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDiaryDto {
    private Long id;
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;
    @NotNull(message = "Class ID is required")
    private Long classId;
    @NotNull(message = "Subject ID is required")
    private Long subjectId;
    @NotNull(message = "Entry date is required")
    private LocalDate entryDate;
    @NotNull(message = "Period number is required")
    private Integer periodNumber;
    @NotBlank(message = "Topics covered is required")
    private String topicsCovered;
    private String teachingMethodology;
    private String reflectionNotes;
    private String boardStateSummary;
}
