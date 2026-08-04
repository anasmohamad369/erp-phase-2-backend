package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.RecordingDto;
import com.classroom.board.service.RecordingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recordings")
@RequiredArgsConstructor
@Tag(name = "Recordings Module", description = "Digital Board Classroom Audio/Video Recorded Sessions")
public class RecordingController {

    private final RecordingService recordingService;

    @PostMapping
    @Operation(summary = "Save classroom recorded session metadata")
    public ResponseEntity<ApiResponse<RecordingDto>> saveRecording(@Valid @RequestBody RecordingDto dto) {
        RecordingDto response = recordingService.saveRecording(dto);
        return ResponseEntity.ok(ApiResponse.success("Session recording saved successfully", response));
    }

    @GetMapping("/class/{classId}/subject/{subjectId}")
    @Operation(summary = "Get recorded sessions for class and subject")
    public ResponseEntity<ApiResponse<List<RecordingDto>>> getRecordingsForClassAndSubject(
            @PathVariable Long classId,
            @PathVariable Long subjectId) {
        return ResponseEntity.ok(ApiResponse.success(recordingService.getRecordingsForClassAndSubject(classId, subjectId)));
    }
}
