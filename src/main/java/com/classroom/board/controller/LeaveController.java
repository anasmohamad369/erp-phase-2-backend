package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.common.enums.LeaveStatus;
import com.classroom.board.dto.LeaveRequestDto;
import com.classroom.board.service.LeaveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/leaves")
@RequiredArgsConstructor
@Tag(name = "Leave Requests Module", description = "Teacher Leave Application & Principal Approval Workflow")
public class LeaveController {

    private final LeaveService leaveService;

    @PostMapping("/apply")
    @PreAuthorize("hasRole('TEACHER')")
    @Operation(summary = "Teacher apply for leave")
    public ResponseEntity<ApiResponse<LeaveRequestDto>> applyLeave(@Valid @RequestBody LeaveRequestDto dto) {
        LeaveRequestDto response = leaveService.applyLeave(dto);
        return ResponseEntity.ok(ApiResponse.success("Leave request submitted", response));
    }

    @PostMapping("/{leaveId}/review")
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Principal review (Approve/Reject) leave request")
    public ResponseEntity<ApiResponse<LeaveRequestDto>> reviewLeave(
            @PathVariable Long leaveId,
            @RequestParam Long principalId,
            @RequestParam LeaveStatus status,
            @RequestParam(required = false) String rejectionReason) {
        LeaveRequestDto response = leaveService.reviewLeave(leaveId, principalId, status, rejectionReason);
        return ResponseEntity.ok(ApiResponse.success("Leave request reviewed", response));
    }

    @GetMapping("/teacher/{teacherId}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PRINCIPAL')")
    @Operation(summary = "Get leave requests for a teacher")
    public ResponseEntity<ApiResponse<List<LeaveRequestDto>>> getTeacherLeaveRequests(@PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(leaveService.getTeacherLeaveRequests(teacherId)));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Get all pending leave requests for principal approval")
    public ResponseEntity<ApiResponse<List<LeaveRequestDto>>> getPendingLeaveRequests() {
        return ResponseEntity.ok(ApiResponse.success(leaveService.getPendingLeaveRequests()));
    }
}
