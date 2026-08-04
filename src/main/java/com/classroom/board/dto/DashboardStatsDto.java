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
public class DashboardStatsDto {
    private long totalTeachers;
    private long totalClassrooms;
    private long totalClasses;
    private long totalStudents;
    private long activeDigitalBoards;
    private long pendingLeaveRequests;
    private long activeSubstitutions;
    private double overallAttendancePercentageToday;
    private List<AnnouncementDto> recentAnnouncements;
}
