package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDate dateOfBirth;
    private String employeeId;
    private String designation;
    private String specialization;
    private LocalDate joiningDate;
    private Long homeRoomClassId;
    private String homeRoomClassName;
    private List<ClassSubjectAssignmentDto> assignments;
}
