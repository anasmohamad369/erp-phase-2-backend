package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.SyllabusProgressDto;
import com.classroom.board.service.SyllabusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/syllabus")
@RequiredArgsConstructor
@Tag(name = "Syllabus Progress Module", description = "Classroom Chapter & Topic Completion Tracking")
public class SyllabusController {

    private final SyllabusService syllabusService;

    @PostMapping("/topic/{topicId}/completion")
    @Operation(summary = "Mark completed topic from Classroom Digital Board")
    public ResponseEntity<ApiResponse<SyllabusProgressDto>> markTopicCompletion(
            @PathVariable Long topicId,
            @RequestParam Long classId,
            @RequestParam Long teacherId,
            @RequestParam(defaultValue = "true") boolean completed,
            @RequestParam(required = false) String remarks) {
        SyllabusProgressDto response = syllabusService.markTopicCompletion(topicId, classId, teacherId, completed, remarks);
        return ResponseEntity.ok(ApiResponse.success("Syllabus progress updated successfully", response));
    }

    @GetMapping("/class/{classId}/subject/{subjectId}")
    @Operation(summary = "Get syllabus completion progress for class and subject")
    public ResponseEntity<ApiResponse<List<SyllabusProgressDto>>> getSyllabusProgress(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(syllabusService.getSyllabusProgress(classId, subjectId)));
    }
}
