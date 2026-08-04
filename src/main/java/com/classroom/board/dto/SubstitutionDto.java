package com.classroom.board.dto;

import com.classroom.board.common.enums.SubstitutionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubstitutionDto {
    private Long id;
    private Long leaveRequestId;
    private Long timetableId;
    private Long originalTeacherId;
    private String originalTeacherName;
    private Long substituteTeacherId;
    private String substituteTeacherName;
    private String className;
    private String subjectName;
    private Integer periodNumber;
    private LocalDate substitutionDate;
    private SubstitutionStatus status;
}
