package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.AttendanceBatchRequest;
import com.classroom.board.dto.AttendanceRecordDto;
import com.classroom.board.service.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attendance")
@RequiredArgsConstructor
@Tag(name = "Attendance Module", description = "Digital Classroom Student Attendance Management")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping("/batch")
    @Operation(summary = "Batch mark student attendance from Classroom Digital Board")
    public ResponseEntity<ApiResponse<List<AttendanceRecordDto>>> markBatchAttendance(
            @Valid @RequestBody AttendanceBatchRequest request) {
        List<AttendanceRecordDto> response = attendanceService.markBatchAttendance(request);
        return ResponseEntity.ok(ApiResponse.success("Attendance marked successfully", response));
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get attendance records for a class on a specific date")
    public ResponseEntity<ApiResponse<List<AttendanceRecordDto>>> getClassAttendanceForDate(
            @PathVariable Long classId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success(attendanceService.getClassAttendanceForDate(classId, date)));
    }
}
