package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.TeacherDiaryDto;
import com.classroom.board.service.DiaryService;
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
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
@Tag(name = "Teacher Diary Module", description = "Classroom Period Reflection & Board State Logs")
public class TeacherDiaryController {

    private final DiaryService diaryService;

    @PostMapping
    @Operation(summary = "Create or update teacher diary entry for a period")
    public ResponseEntity<ApiResponse<TeacherDiaryDto>> createOrUpdateDiary(@Valid @RequestBody TeacherDiaryDto dto) {
        TeacherDiaryDto response = diaryService.createOrUpdateDiary(dto);
        return ResponseEntity.ok(ApiResponse.success("Teacher diary entry saved successfully", response));
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Get teacher diary entries for a date")
    public ResponseEntity<ApiResponse<List<TeacherDiaryDto>>> getTeacherDiaries(
            @PathVariable Long teacherId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(ApiResponse.success(diaryService.getTeacherDiaries(teacherId, date)));
    }
}
