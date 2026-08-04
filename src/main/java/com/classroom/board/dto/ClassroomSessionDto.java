package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassroomSessionDto {
    private ClassroomDto classroom;
    private SchoolClassDto schoolClass;
    private TimetableDto currentPeriod;
    private List<StudentDto> students;
    private List<SyllabusProgressDto> syllabusProgress;
    private List<WorksheetDto> activeWorksheets;
    private List<HomeworkDto> pendingHomework;
    private List<AnnouncementDto> relevantAnnouncements;
}
