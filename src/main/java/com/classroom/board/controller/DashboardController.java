package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.DashboardStatsDto;
import com.classroom.board.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PRINCIPAL')")
@Tag(name = "Principal Dashboard", description = "Principal Web Dashboard Core Stats & Live System Overview")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/principal")
    @Operation(summary = "Get principal web dashboard live metrics & metrics summary")
    public ResponseEntity<ApiResponse<DashboardStatsDto>> getPrincipalDashboardStats() {
        return ResponseEntity.ok(ApiResponse.success(dashboardService.getPrincipalDashboardStats()));
    }
}
