package com.classroom.board.controller;

import com.classroom.board.common.dto.ApiResponse;
import com.classroom.board.dto.PrincipalDto;
import com.classroom.board.service.PrincipalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/principals")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PRINCIPAL')")
@Tag(name = "Principal Management", description = "Principal Administrative Directory")
public class PrincipalController {

    private final PrincipalService principalService;

    @GetMapping
    @Operation(summary = "Get list of all principals")
    public ResponseEntity<ApiResponse<List<PrincipalDto>>> getAllPrincipals() {
        return ResponseEntity.ok(ApiResponse.success(principalService.getAllPrincipals()));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get principal by ID")
    public ResponseEntity<ApiResponse<PrincipalDto>> getPrincipalById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(principalService.getPrincipalById(id)));
    }
}
