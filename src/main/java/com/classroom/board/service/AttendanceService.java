package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.AttendanceBatchRequest;
import com.classroom.board.dto.AttendanceRecordDto;
import com.classroom.board.entity.Attendance;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.Student;
import com.classroom.board.entity.Teacher;
import com.classroom.board.repository.AttendanceRepository;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.StudentRepository;
import com.classroom.board.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    public List<AttendanceRecordDto> markBatchAttendance(AttendanceBatchRequest request) {
        SchoolClass schoolClass = schoolClassRepository.findById(request.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class", "id", request.getClassId()));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", request.getTeacherId()));

        List<AttendanceRecordDto> savedRecords = new ArrayList<>();

        for (AttendanceRecordDto record : request.getRecords()) {
            Student student = studentRepository.findById(record.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student", "id", record.getStudentId()));

            Attendance attendance = attendanceRepository.findByStudentIdAndDate(student.getId(), request.getDate())
                    .orElseGet(() -> Attendance.builder()
                            .student(student)
                            .schoolClass(schoolClass)
                            .teacher(teacher)
                            .date(request.getDate())
                            .build());

            attendance.setStatus(record.getStatus());
            attendance.setRemarks(record.getRemarks());

            Attendance saved = attendanceRepository.save(attendance);

            savedRecords.add(AttendanceRecordDto.builder()
                    .id(saved.getId())
                    .studentId(student.getId())
                    .studentName(student.getFirstName() + " " + student.getLastName())
                    .rollNumber(student.getRollNumber())
                    .status(saved.getStatus())
                    .remarks(saved.getRemarks())
                    .date(saved.getDate())
                    .build());
        }

        log.info("Successfully marked attendance for {} students in Class {}", savedRecords.size(), schoolClass.getId());
        return savedRecords;
    }

    @Transactional(readOnly = true)
    public List<AttendanceRecordDto> getClassAttendanceForDate(Long classId, LocalDate date) {
        return attendanceRepository.findBySchoolClassIdAndDate(classId, date).stream()
                .map(a -> AttendanceRecordDto.builder()
                        .id(a.getId())
                        .studentId(a.getStudent().getId())
                        .studentName(a.getStudent().getFirstName() + " " + a.getStudent().getLastName())
                        .rollNumber(a.getStudent().getRollNumber())
                        .status(a.getStatus())
                        .remarks(a.getRemarks())
                        .date(a.getDate())
                        .build())
                .toList();
    }
}
