package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.TeacherDto;
import com.classroom.board.entity.Teacher;
import com.classroom.board.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

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

    public TeacherDto mapToDto(Teacher teacher) {
        return TeacherDto.builder()
                .id(teacher.getId())
                .userId(teacher.getUser().getId())
                .username(teacher.getUser().getUsername())
                .email(teacher.getUser().getEmail())
                .firstName(teacher.getUser().getFirstName())
                .lastName(teacher.getUser().getLastName())
                .phone(teacher.getUser().getPhone())
                .employeeId(teacher.getEmployeeId())
                .designation(teacher.getDesignation())
                .specialization(teacher.getSpecialization())
                .joiningDate(teacher.getJoiningDate())
                .build();
    }
}
