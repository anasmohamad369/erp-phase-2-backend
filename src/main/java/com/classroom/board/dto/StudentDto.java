package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String rollNumber;
    private String firstName;
    private String lastName;
    private String admissionNumber;
    private String gender;
    private String hostelBlock;
    private String roomNumber;
    private Long classId;
    private String className;
}
