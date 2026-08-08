package com.classroom.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassSubjectAssignmentDto {

    @NotNull(message = "Class ID is required")
    private Long classId;

    private String className;

    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    private String subjectName;

    private String academicYear;
}
