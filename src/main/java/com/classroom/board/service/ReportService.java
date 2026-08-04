package com.classroom.board.service;

import com.classroom.board.common.enums.AttendanceStatus;
import com.classroom.board.dto.ReportSummaryDto;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.Subject;
import com.classroom.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceRepository attendanceRepository;
    private final SyllabusProgressRepository syllabusProgressRepository;

    @Transactional(readOnly = true)
    public ReportSummaryDto generateClassroomSummaryReport(String academicYear) {
        List<SchoolClass> classes = schoolClassRepository.findAll();
        List<Subject> subjects = subjectRepository.findAll();

        Map<String, Double> classAttendanceRates = new HashMap<>();
        for (SchoolClass sc : classes) {
            String className = sc.getGrade() + "-" + sc.getSection();
            long totalPresent = attendanceRepository.countBySchoolClassIdAndDateAndStatus(sc.getId(), LocalDate.now(), AttendanceStatus.PRESENT);
            classAttendanceRates.put(className, (double) totalPresent);
        }

        Map<String, Double> subjectSyllabusCompletionRates = new HashMap<>();
        for (Subject s : subjects) {
            long totalCompleted = syllabusProgressRepository.countBySchoolClassIdAndTopicChapterSubjectId(1L, s.getId());
            subjectSyllabusCompletionRates.put(s.getName(), (double) totalCompleted);
        }

        Map<String, Object> summaryMetrics = new HashMap<>();
        summaryMetrics.put("totalClassesAnalyzed", classes.size());
        summaryMetrics.put("totalSubjectsAnalyzed", subjects.size());
        summaryMetrics.put("generatedAt", LocalDate.now().toString());

        return ReportSummaryDto.builder()
                .reportType("COMPREHENSIVE_CLASSROOM_DIGITAL_BOARD_SUMMARY")
                .academicYear(academicYear != null ? academicYear : "2025-2026")
                .totalRecordsProcessed(classes.size() + subjects.size())
                .summaryMetrics(summaryMetrics)
                .classAttendanceRates(classAttendanceRates)
                .subjectSyllabusCompletionRates(subjectSyllabusCompletionRates)
                .build();
    }
}
