package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.ReportSummaryDto;
import com.classroom.board.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PRINCIPAL')")
@Tag(name = "Reports & Analytics", description = "Principal Web Dashboard Analytics & Exportable Summaries")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/summary")
    @Operation(summary = "Generate overall classroom digital board summary analytics report")
    public ResponseEntity<ApiResponse<ReportSummaryDto>> generateClassroomSummaryReport(
            @RequestParam(required = false) String academicYear) {
        return ResponseEntity.ok(ApiResponse.success(reportService.generateClassroomSummaryReport(academicYear)));
    }
}
