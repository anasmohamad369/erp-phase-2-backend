package com.classroom.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportSummaryDto {
    private String reportType;
    private String academicYear;
    private long totalRecordsProcessed;
    private Map<String, Object> summaryMetrics;
    private Map<String, Double> classAttendanceRates;
    private Map<String, Double> subjectSyllabusCompletionRates;
}
