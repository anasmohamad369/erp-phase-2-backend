package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.TimetableDto;
import com.classroom.board.service.TimetableService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/timetable")
@RequiredArgsConstructor
@Tag(name = "Timetable Module", description = "Teacher and Class Daily Timetables")
public class TimetableController {

    private final TimetableService timetableService;

    @GetMapping("/teacher/{teacherId}/today")
    @Operation(summary = "Get today's timetable for teacher (Digital Board desktop launch view)")
    public ResponseEntity<ApiResponse<List<TimetableDto>>> getTodayTimetableForTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(ApiResponse.success(timetableService.getTeacherTimetableForToday(teacherId)));
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get teacher timetable by specific day")
    public ResponseEntity<ApiResponse<List<TimetableDto>>> getTeacherTimetableByDay(
            @PathVariable Long teacherId,
            @RequestParam String dayOfWeek) {
        return ResponseEntity.ok(ApiResponse.success(timetableService.getTeacherTimetableByDay(teacherId, dayOfWeek)));
    }

    @GetMapping("/class/{classId}")
    @Operation(summary = "Get class timetable by specific day")
    public ResponseEntity<ApiResponse<List<TimetableDto>>> getClassTimetableByDay(
            @PathVariable Long classId,
            @RequestParam String dayOfWeek) {
        return ResponseEntity.ok(ApiResponse.success(timetableService.getClassTimetableByDay(classId, dayOfWeek)));
    }
}
