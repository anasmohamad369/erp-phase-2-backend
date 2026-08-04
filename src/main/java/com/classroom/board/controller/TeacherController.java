package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.TeacherDto;
import com.classroom.board.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Management", description = "Teacher Directory & Profiles")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Get list of all teachers (Principal only)")
    public ResponseEntity<ApiResponse<List<TeacherDto>>> getAllTeachers() {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getAllTeachers()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PRINCIPAL')")
    @Operation(summary = "Get teacher profile by ID")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getTeacherById(id)));
    }
}
