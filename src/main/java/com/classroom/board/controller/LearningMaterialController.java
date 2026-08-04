package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.entity.FileStorage;
import com.classroom.board.service.LearningMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/materials")
@RequiredArgsConstructor
@Tag(name = "Learning Materials Module", description = "Digital Board Classroom Study Content & Unit Files")
public class LearningMaterialController {

    private final LearningMaterialService learningMaterialService;

    @GetMapping
    @Operation(summary = "Get list of all uploaded learning materials")
    public ResponseEntity<ApiResponse<List<FileStorage>>> getAllMaterials() {
        return ResponseEntity.ok(ApiResponse.success(learningMaterialService.getAllMaterials()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get learning material file metadata by ID")
    public ResponseEntity<ApiResponse<FileStorage>> getMaterialById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(learningMaterialService.getMaterialById(id)));
    }
}
