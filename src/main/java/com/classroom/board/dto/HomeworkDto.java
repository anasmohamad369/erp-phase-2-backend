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
public class HomeworkDto {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Instructions are required")
    private String instructions;
    @NotNull(message = "Class ID is required")
    private Long classId;
    @NotNull(message = "Subject ID is required")
    private Long subjectId;
    @NotNull(message = "Teacher ID is required")
    private Long teacherId;
    private Long fileId;
    private String fileName;
    private String fileDownloadUrl;
    private LocalDate assignedDate;
    @NotNull(message = "Due date is required")
    private LocalDate dueDate;
}
