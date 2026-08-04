package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private Long recipientId;
    private String title;
    private String message;
    private String type;
    private Boolean isRead;
    private Instant createdAt;
}
