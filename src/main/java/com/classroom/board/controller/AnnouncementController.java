package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.common.enums.Role;
import com.classroom.board.dto.AnnouncementDto;
import com.classroom.board.service.AnnouncementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/announcements")
@RequiredArgsConstructor
@Tag(name = "Announcements Module", description = "Digital Classroom Broadcasts & School Notices")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @PostMapping
    @Operation(summary = "Publish announcement")
    public ResponseEntity<ApiResponse<AnnouncementDto>> createAnnouncement(@Valid @RequestBody AnnouncementDto dto) {
        AnnouncementDto response = announcementService.createAnnouncement(dto);
        return ResponseEntity.ok(ApiResponse.success("Announcement published successfully", response));
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get announcements targeted to specific role")
    public ResponseEntity<ApiResponse<List<AnnouncementDto>>> getAnnouncementsForRole(@PathVariable Role role) {
        return ResponseEntity.ok(ApiResponse.success(announcementService.getAnnouncementsForRole(role)));
    }
}
