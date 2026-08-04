package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableDto {
    private Long id;
    private Long classId;
    private String className;
    private Long subjectId;
    private String subjectName;
    private Long teacherId;
    private String teacherName;
    private Long classroomId;
    private String roomNumber;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer periodNumber;
    private String academicYear;
}
