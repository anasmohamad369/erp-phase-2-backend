package com.classroom.board.service;

import com.classroom.board.common.enums.LeaveStatus;
import com.classroom.board.common.enums.Role;
import com.classroom.board.dto.DashboardStatsDto;
import com.classroom.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final SubstitutionRepository substitutionRepository;
    private final AnnouncementService announcementService;

    @Transactional(readOnly = true)
    public DashboardStatsDto getPrincipalDashboardStats() {
        long totalTeachers = teacherRepository.count();
        long totalClassrooms = classroomRepository.count();
        long totalClasses = schoolClassRepository.count();
        long totalStudents = studentRepository.count();

        long pendingLeaves = leaveRequestRepository.findByStatus(LeaveStatus.PENDING).size();
        long activeSubstitutions = substitutionRepository.findBySubstitutionDate(LocalDate.now()).size();

        return DashboardStatsDto.builder()
                .totalTeachers(totalTeachers)
                .totalClassrooms(totalClassrooms)
                .totalClasses(totalClasses)
                .totalStudents(totalStudents)
                .activeDigitalBoards(totalClassrooms)
                .pendingLeaveRequests(pendingLeaves)
                .activeSubstitutions(activeSubstitutions)
                .overallAttendancePercentageToday(94.5)
                .recentAnnouncements(announcementService.getAnnouncementsForRole(Role.ROLE_TEACHER))
                .build();
    }
}
