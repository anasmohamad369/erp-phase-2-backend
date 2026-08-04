package com.classroom.board.dto;

import com.classroom.board.common.enums.AttendanceStatus;
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
public class AttendanceRecordDto {
    private Long id;
    @NotNull(message = "Student ID is required")
    private Long studentId;
    private String studentName;
    private String rollNumber;
    @NotNull(message = "Attendance status is required")
    private AttendanceStatus status;
    private String remarks;
    private LocalDate date;
}
