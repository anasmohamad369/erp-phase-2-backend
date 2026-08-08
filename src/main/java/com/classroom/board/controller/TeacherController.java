package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.CreateTeacherRequest;
import com.classroom.board.dto.TeacherDto;
import com.classroom.board.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
@Tag(name = "Teacher Management", description = "Teacher Directory, Creation & Profiles")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Get list of all teachers (Principal/Admin only)")
    public ResponseEntity<ApiResponse<List<TeacherDto>>> getAllTeachers() {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getAllTeachers()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER', 'PRINCIPAL')")
    @Operation(summary = "Get teacher profile by ID")
    public ResponseEntity<ApiResponse<TeacherDto>> getTeacherById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(teacherService.getTeacherById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Create/Add a new teacher (Principal/Admin only)")
    public ResponseEntity<ApiResponse<TeacherDto>> createTeacher(@Valid @RequestBody CreateTeacherRequest request) {
        TeacherDto createdTeacher = teacherService.createTeacher(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Teacher created successfully", createdTeacher));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('PRINCIPAL')")
    @Operation(summary = "Update an existing teacher details (Principal/Admin only)")
    public ResponseEntity<ApiResponse<TeacherDto>> updateTeacher(@PathVariable Long id, @Valid @RequestBody CreateTeacherRequest request) {
        TeacherDto updatedTeacher = teacherService.updateTeacher(id, request);
        return ResponseEntity.ok(ApiResponse.success("Teacher updated successfully", updatedTeacher));
    }
}
