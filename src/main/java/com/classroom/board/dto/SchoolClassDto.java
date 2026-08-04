package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDto {
    private Long id;
    private String grade;
    private String section;
    private String academicYear;
    private Long classTeacherId;
    private String classTeacherName;
}
