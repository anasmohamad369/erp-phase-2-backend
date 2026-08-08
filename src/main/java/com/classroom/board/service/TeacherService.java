package com.classroom.board.service;

import com.classroom.board.common.enums.Role;
import com.classroom.board.common.exception.BadRequestException;
import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.ClassSubjectAssignmentDto;
import com.classroom.board.dto.CreateTeacherRequest;
import com.classroom.board.dto.TeacherDto;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.Subject;
import com.classroom.board.entity.Teacher;
import com.classroom.board.entity.TeacherAssignment;
import com.classroom.board.entity.User;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.SubjectRepository;
import com.classroom.board.repository.TeacherAssignmentRepository;
import com.classroom.board.repository.TeacherRepository;
import com.classroom.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherAssignmentRepository teacherAssignmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));
        return mapToDto(teacher);
    }

    @Transactional(readOnly = true)
    public TeacherDto getTeacherByUserId(Long userId) {
        Teacher teacher = teacherRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "userId", userId));
        return mapToDto(teacher);
    }

    @Transactional
    public TeacherDto createTeacher(CreateTeacherRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email '" + request.getEmail() + "' is already in use");
        }
        if (teacherRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new BadRequestException("Employee ID '" + request.getEmployeeId() + "' already exists");
        }

        String rawPassword = (request.getPassword() != null && !request.getPassword().isBlank())
                ? request.getPassword()
                : "Teacher@123";

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(rawPassword))
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .role(Role.ROLE_TEACHER)
                .enabled(true)
                .deleted(false)
                .build();

        user = userRepository.save(user);

        Teacher teacher = Teacher.builder()
                .user(user)
                .employeeId(request.getEmployeeId())
                .designation(request.getDesignation())
                .specialization(request.getSpecialization())
                .joiningDate(request.getJoiningDate())
                .build();

        teacher = teacherRepository.save(teacher);

        // Assign Home Room Class Teacher if provided
        if (request.getHomeRoomClassId() != null) {
            SchoolClass schoolClass = schoolClassRepository.findById(request.getHomeRoomClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("SchoolClass", "id", request.getHomeRoomClassId()));
            schoolClass.setClassTeacher(teacher);
            schoolClassRepository.save(schoolClass);
        }

        // Persist Class and Subject Assignments if provided
        if (request.getAssignments() != null && !request.getAssignments().isEmpty()) {
            for (ClassSubjectAssignmentDto assignmentDto : request.getAssignments()) {
                SchoolClass sc = schoolClassRepository.findById(assignmentDto.getClassId())
                        .orElseThrow(() -> new ResourceNotFoundException("SchoolClass", "id", assignmentDto.getClassId()));
                Subject sub = subjectRepository.findById(assignmentDto.getSubjectId())
                        .orElseThrow(() -> new ResourceNotFoundException("Subject", "id", assignmentDto.getSubjectId()));

                String academicYear = assignmentDto.getAcademicYear() != null
                        ? assignmentDto.getAcademicYear()
                        : sc.getAcademicYear();

                TeacherAssignment assignment = TeacherAssignment.builder()
                        .teacher(teacher)
                        .schoolClass(sc)
                        .subject(sub)
                        .academicYear(academicYear)
                        .build();

                teacherAssignmentRepository.save(assignment);
            }
        }

        return mapToDto(teacher);
    }

    @Transactional
    public TeacherDto updateTeacher(Long id, CreateTeacherRequest request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", id));

        User user = teacher.getUser();

        // Check username uniqueness if changed
        if (!user.getUsername().equalsIgnoreCase(request.getUsername()) && userRepository.existsByUsername(request.getUsername())) {
            throw new BadRequestException("Username '" + request.getUsername() + "' is already taken");
        }
        // Check email uniqueness if changed
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email '" + request.getEmail() + "' is already in use");
        }

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setDateOfBirth(request.getDateOfBirth());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        userRepository.save(user);

        teacher.setDesignation(request.getDesignation());
        teacher.setSpecialization(request.getSpecialization());
        if (request.getJoiningDate() != null) {
            teacher.setJoiningDate(request.getJoiningDate());
        }
        teacherRepository.save(teacher);

        // Update home room class assignment
        List<SchoolClass> currentHomeRooms = schoolClassRepository.findByClassTeacherId(teacher.getId());
        for (SchoolClass sc : currentHomeRooms) {
            if (request.getHomeRoomClassId() == null || !sc.getId().equals(request.getHomeRoomClassId())) {
                sc.setClassTeacher(null);
                schoolClassRepository.save(sc);
            }
        }
        if (request.getHomeRoomClassId() != null) {
            SchoolClass newHomeRoom = schoolClassRepository.findById(request.getHomeRoomClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("SchoolClass", "id", request.getHomeRoomClassId()));
            newHomeRoom.setClassTeacher(teacher);
            schoolClassRepository.save(newHomeRoom);
        }

        return mapToDto(teacher);
    }

    public TeacherDto mapToDto(Teacher teacher) {
        List<SchoolClass> homeRoomClasses = schoolClassRepository.findByClassTeacherId(teacher.getId());
        Long homeRoomClassId = homeRoomClasses.isEmpty() ? null : homeRoomClasses.get(0).getId();
        String homeRoomClassName = homeRoomClasses.isEmpty()
                ? null
                : homeRoomClasses.get(0).getGrade() + "-" + homeRoomClasses.get(0).getSection();

        List<TeacherAssignment> assignments = teacherAssignmentRepository.findByTeacherId(teacher.getId());
        List<ClassSubjectAssignmentDto> assignmentDtos = assignments.stream()
                .map(a -> ClassSubjectAssignmentDto.builder()
                        .classId(a.getSchoolClass().getId())
                        .className(a.getSchoolClass().getGrade() + "-" + a.getSchoolClass().getSection())
                        .subjectId(a.getSubject().getId())
                        .subjectName(a.getSubject().getName())
                        .academicYear(a.getAcademicYear())
                        .build())
                .toList();

        return TeacherDto.builder()
                .id(teacher.getId())
                .userId(teacher.getUser().getId())
                .username(teacher.getUser().getUsername())
                .email(teacher.getUser().getEmail())
                .firstName(teacher.getUser().getFirstName())
                .lastName(teacher.getUser().getLastName())
                .phone(teacher.getUser().getPhone())
                .dateOfBirth(teacher.getUser().getDateOfBirth())
                .employeeId(teacher.getEmployeeId())
                .designation(teacher.getDesignation())
                .specialization(teacher.getSpecialization())
                .joiningDate(teacher.getJoiningDate())
                .homeRoomClassId(homeRoomClassId)
                .homeRoomClassName(homeRoomClassName)
                .assignments(assignmentDtos)
                .build();
    }
}
