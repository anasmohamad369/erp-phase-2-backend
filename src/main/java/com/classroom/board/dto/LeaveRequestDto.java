package com.classroom.board.dto;

import com.classroom.board.common.enums.LeaveStatus;
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
public class LeaveRequestDto {
    private Long id;
    private Long teacherId;
    private String teacherName;
    @NotNull(message = "Start date is required")
    private LocalDate startDate;
    @NotNull(message = "End date is required")
    private LocalDate endDate;
    @NotBlank(message = "Reason is required")
    private String reason;
    private LeaveStatus status;
    private Long reviewedById;
    private String reviewedByName;
    private String rejectionReason;
}
