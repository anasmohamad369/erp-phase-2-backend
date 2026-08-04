package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.WorksheetDto;
import com.classroom.board.service.WorksheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/worksheets")
@RequiredArgsConstructor
@Tag(name = "Worksheets Module", description = "Classroom Practice Worksheets")
public class WorksheetController {

    private final WorksheetService worksheetService;

    @PostMapping
    @Operation(summary = "Upload and assign worksheet")
    public ResponseEntity<ApiResponse<WorksheetDto>> createWorksheet(@Valid @RequestBody WorksheetDto dto) {
        WorksheetDto response = worksheetService.createWorksheet(dto);
        return ResponseEntity.ok(ApiResponse.success("Worksheet created successfully", response));
    }

    @GetMapping("/class/{classId}/subject/{subjectId}")
    @Operation(summary = "Get worksheets for class and subject")
    public ResponseEntity<ApiResponse<List<WorksheetDto>>> getWorksheetsForClassAndSubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(worksheetService.getWorksheetsForClassAndSubject(classId, subjectId)));
    }
}
