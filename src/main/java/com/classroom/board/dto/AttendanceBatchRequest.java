package com.classroom.board.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceBatchRequest {

    @NotNull(message = "Class ID is required")
    private Long classId;

    @NotNull(message = "Teacher ID is required")
    private Long teacherId;

    @NotNull(message = "Date is required")
    private LocalDate date;

    @NotEmpty(message = "Attendance list cannot be empty")
    @Valid
    private List<AttendanceRecordDto> records;
}
