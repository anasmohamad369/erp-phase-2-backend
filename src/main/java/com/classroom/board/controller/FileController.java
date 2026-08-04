package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.entity.FileStorage;
import com.classroom.board.security.UserPrincipal;
import com.classroom.board.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Tag(name = "File Management", description = "Multipart File Uploads, Downloads & Digital Board Attachments")
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping("/upload")
    @Operation(summary = "Upload multipart file for worksheets, homework, notes or recordings")
    public ResponseEntity<ApiResponse<FileStorage>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        FileStorage savedFile = fileStorageService.storeFile(file, userPrincipal.getId());
        return ResponseEntity.ok(ApiResponse.success("File uploaded successfully", savedFile));
    }

    @GetMapping("/download/{fileId}")
    @Operation(summary = "Download stored file by file ID")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        FileStorage metadata = fileStorageService.getFileMetadata(fileId);
        Resource resource = fileStorageService.loadFileAsResource(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(metadata.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + metadata.getFileName() + "\"")
                .body(resource);
    }
}
