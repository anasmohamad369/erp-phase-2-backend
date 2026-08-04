package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String role;
    private Long teacherId;
    private String employeeId;
    private String designation;
    private String specialization;
    private Long principalId;
    private String qualification;
    private String officeRoom;
}
