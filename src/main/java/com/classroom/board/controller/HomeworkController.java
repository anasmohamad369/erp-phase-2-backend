package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.HomeworkDto;
import com.classroom.board.service.HomeworkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/homework")
@RequiredArgsConstructor
@Tag(name = "Homework Module", description = "Classroom Homework Assignment & Digital Files")
public class HomeworkController {

    private final HomeworkService homeworkService;

    @PostMapping
    @Operation(summary = "Assign homework to class")
    public ResponseEntity<ApiResponse<HomeworkDto>> createHomework(@Valid @RequestBody HomeworkDto dto) {
        HomeworkDto response = homeworkService.createHomework(dto);
        return ResponseEntity.ok(ApiResponse.success("Homework assigned successfully", response));
    }

    @GetMapping("/class/{classId}/subject/{subjectId}")
    @Operation(summary = "Get homework for class and subject")
    public ResponseEntity<ApiResponse<List<HomeworkDto>>> getHomeworkForClassAndSubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(homeworkService.getHomeworkForClassAndSubject(classId, subjectId)));
    }
}
