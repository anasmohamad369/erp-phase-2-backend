package com.classroom.board.dto;

import com.classroom.board.common.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDto {
    private Long id;
    @NotBlank(message = "Title is required")
    private String title;
    @NotBlank(message = "Content is required")
    private String content;
    private Long authorId;
    private String authorName;
    private Role targetRole;
    private Long targetClassId;
    private String targetClassName;
    private Boolean isUrgent;
    private Instant createdAt;
}
