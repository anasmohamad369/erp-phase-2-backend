package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.*;
import com.classroom.board.entity.Classroom;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final StudentRepository studentRepository;
    private final TimetableRepository timetableRepository;
    private final WorksheetRepository worksheetRepository;
    private final HomeworkRepository homeworkRepository;
    private final SyllabusProgressRepository syllabusProgressRepository;
    private final AnnouncementRepository announcementRepository;

    @Transactional(readOnly = true)
    public List<ClassroomDto> getAllClassrooms() {
        return classroomRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClassroomDto getClassroomById(Long id) {
        Classroom classroom = classroomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", id));
        return mapToDto(classroom);
    }

    @Transactional(readOnly = true)
    public ClassroomDto getClassroomByDeviceId(String deviceId) {
        Classroom classroom = classroomRepository.findByDigitalBoardDeviceId(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom", "digitalBoardDeviceId", deviceId));
        return mapToDto(classroom);
    }

    @Transactional(readOnly = true)
    public ClassroomSessionDto loadClassroomSession(Long classroomId, Long teacherId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new ResourceNotFoundException("Classroom", "id", classroomId));

        SchoolClass currentClass = classroom.getCurrentClass();
        if (currentClass == null) {
            throw new ResourceNotFoundException("No active class assigned to classroom " + classroom.getRoomNumber());
        }

        String todayName = LocalDate.now().getDayOfWeek().name();
        List<TimetableDto> todayTimetable = timetableRepository
                .findByClassroomIdAndDayOfWeekOrderByPeriodNumberAsc(classroomId, todayName)
                .stream()
                .map(t -> TimetableDto.builder()
                        .id(t.getId())
                        .classId(t.getSchoolClass().getId())
                        .className(t.getSchoolClass().getGrade() + "-" + t.getSchoolClass().getSection())
                        .subjectId(t.getSubject().getId())
                        .subjectName(t.getSubject().getName())
                        .teacherId(t.getTeacher().getId())
                        .teacherName(t.getTeacher().getUser().getFirstName() + " " + t.getTeacher().getUser().getLastName())
                        .classroomId(t.getClassroom().getId())
                        .roomNumber(t.getClassroom().getRoomNumber())
                        .dayOfWeek(t.getDayOfWeek())
                        .startTime(t.getStartTime())
                        .endTime(t.getEndTime())
                        .periodNumber(t.getPeriodNumber())
                        .academicYear(t.getAcademicYear())
                        .build())
                .toList();

        TimetableDto currentPeriod = todayTimetable.isEmpty() ? null : todayTimetable.get(0);

        List<StudentDto> students = studentRepository.findBySchoolClassIdOrderByRollNumberAsc(currentClass.getId())
                .stream()
                .map(s -> StudentDto.builder()
                        .id(s.getId())
                        .rollNumber(s.getRollNumber())
                        .firstName(s.getFirstName())
                        .lastName(s.getLastName())
                        .admissionNumber(s.getAdmissionNumber())
                        .gender(s.getGender())
                        .hostelBlock(s.getHostelBlock())
                        .roomNumber(s.getRoomNumber())
                        .classId(s.getSchoolClass().getId())
                        .className(s.getSchoolClass().getGrade() + "-" + s.getSchoolClass().getSection())
                        .build())
                .toList();

        List<WorksheetDto> activeWorksheets = worksheetRepository
                .findBySchoolClassIdAndSubjectId(currentClass.getId(), currentPeriod != null ? currentPeriod.getSubjectId() : 1L)
                .stream()
                .map(w -> WorksheetDto.builder()
                        .id(w.getId())
                        .title(w.getTitle())
                        .description(w.getDescription())
                        .classId(w.getSchoolClass().getId())
                        .subjectId(w.getSubject().getId())
                        .teacherId(w.getTeacher().getId())
                        .fileId(w.getFile() != null ? w.getFile().getId() : null)
                        .fileName(w.getFile() != null ? w.getFile().getFileName() : null)
                        .dueDate(w.getDueDate())
                        .build())
                .toList();

        List<HomeworkDto> pendingHomework = homeworkRepository
                .findBySchoolClassIdAndSubjectId(currentClass.getId(), currentPeriod != null ? currentPeriod.getSubjectId() : 1L)
                .stream()
                .map(h -> HomeworkDto.builder()
                        .id(h.getId())
                        .title(h.getTitle())
                        .instructions(h.getInstructions())
                        .classId(h.getSchoolClass().getId())
                        .subjectId(h.getSubject().getId())
                        .teacherId(h.getTeacher().getId())
                        .fileId(h.getFile() != null ? h.getFile().getId() : null)
                        .fileName(h.getFile() != null ? h.getFile().getFileName() : null)
                        .assignedDate(h.getAssignedDate())
                        .dueDate(h.getDueDate())
                        .build())
                .toList();

        return ClassroomSessionDto.builder()
                .classroom(mapToDto(classroom))
                .schoolClass(SchoolClassDto.builder()
                        .id(currentClass.getId())
                        .grade(currentClass.getGrade())
                        .section(currentClass.getSection())
                        .academicYear(currentClass.getAcademicYear())
                        .classTeacherId(currentClass.getClassTeacher() != null ? currentClass.getClassTeacher().getId() : null)
                        .build())
                .currentPeriod(currentPeriod)
                .students(students)
                .activeWorksheets(activeWorksheets)
                .pendingHomework(pendingHomework)
                .build();
    }

    public ClassroomDto mapToDto(Classroom c) {
        return ClassroomDto.builder()
                .id(c.getId())
                .roomNumber(c.getRoomNumber())
                .building(c.getBuilding())
                .digitalBoardDeviceId(c.getDigitalBoardDeviceId())
                .ipAddress(c.getIpAddress())
                .isActive(c.getIsActive())
                .currentClassId(c.getCurrentClass() != null ? c.getCurrentClass().getId() : null)
                .currentClassName(c.getCurrentClass() != null ? c.getCurrentClass().getGrade() + "-" + c.getCurrentClass().getSection() : "N/A")
                .build();
    }
}
