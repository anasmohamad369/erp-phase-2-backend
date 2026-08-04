package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomDto {
    private Long id;
    private String roomNumber;
    private String building;
    private String digitalBoardDeviceId;
    private String ipAddress;
    private Boolean isActive;
    private Long currentClassId;
    private String currentClassName;
}
