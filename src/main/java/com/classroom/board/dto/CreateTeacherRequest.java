package com.classroom.board.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CreateTeacherRequest {

    @NotBlank(message = "Username is required")
    private String username;

    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phone;

    private LocalDate dateOfBirth;

    @NotBlank(message = "Employee ID is required")
    private String employeeId;

    @NotBlank(message = "Designation is required")
    private String designation;

    private String specialization;

    private LocalDate joiningDate;

    private Long homeRoomClassId;

    private List<ClassSubjectAssignmentDto> assignments;
}
