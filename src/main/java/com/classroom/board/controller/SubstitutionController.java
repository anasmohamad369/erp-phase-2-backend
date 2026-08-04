package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.SubstitutionDto;
import com.classroom.board.service.SubstitutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/substitutions")
@RequiredArgsConstructor
@Tag(name = "Teacher Substitutions Module", description = "Classroom Period Teacher Substitutions")
public class SubstitutionController {

    private final SubstitutionService substitutionService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Assign substitute teacher for a period (Principal feature)")
    public ResponseEntity<ApiResponse<SubstitutionDto>> assignSubstitution(
            @RequestParam Long timetableId,
            @RequestParam Long originalTeacherId,
            @RequestParam Long substituteTeacherId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        SubstitutionDto response = substitutionService.assignSubstitution(timetableId, originalTeacherId, substituteTeacherId, date);
        return ResponseEntity.ok(ApiResponse.success("Substitute teacher assigned successfully", response));
    }

    @GetMapping("/teacher/{substituteTeacherId}/today")
    @PreAuthorize("hasAnyRole('TEACHER', 'PRINCIPAL')")
    @Operation(summary = "Get today's substituted classes for a teacher")
    public ResponseEntity<ApiResponse<List<SubstitutionDto>>> getSubstitutionsForTeacherToday(@PathVariable Long substituteTeacherId) {
        return ResponseEntity.ok(ApiResponse.success(substitutionService.getSubstitutionsForTeacherToday(substituteTeacherId)));
    }
}
