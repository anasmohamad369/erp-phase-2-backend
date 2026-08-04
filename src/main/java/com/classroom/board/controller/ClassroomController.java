package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.ClassroomDto;
import com.classroom.board.dto.ClassroomSessionDto;
import com.classroom.board.service.ClassroomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
@RequiredArgsConstructor
@Tag(name = "Classroom & Digital Board", description = "Digital Board Room Status & Classroom Desktop Session Loading")
public class ClassroomController {

    private final ClassroomService classroomService;

    @GetMapping
    @Operation(summary = "Get list of all classrooms")
    public ResponseEntity<ApiResponse<List<ClassroomDto>>> getAllClassrooms() {
        return ResponseEntity.ok(ApiResponse.success(classroomService.getAllClassrooms()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get classroom details by ID")
    public ResponseEntity<ApiResponse<ClassroomDto>> getClassroomById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(classroomService.getClassroomById(id)));
    }

    @GetMapping("/device/{deviceId}")
    @Operation(summary = "Get classroom details by Windows Digital Board Device Hardware ID")
    public ResponseEntity<ApiResponse<ClassroomDto>> getClassroomByDeviceId(@PathVariable String deviceId) {
        return ResponseEntity.ok(ApiResponse.success(classroomService.getClassroomByDeviceId(deviceId)));
    }

    @GetMapping("/{id}/session")
    @Operation(summary = "Core Digital Board Desktop Flow: Load active classroom session, students, timetable, worksheets & homework")
    public ResponseEntity<ApiResponse<ClassroomSessionDto>> loadClassroomSession(
            @PathVariable Long id,
            @RequestParam Long teacherId) {
        ClassroomSessionDto session = classroomService.loadClassroomSession(id, teacherId);
        return ResponseEntity.ok(ApiResponse.success("Classroom digital board session loaded successfully", session));
    }
}
