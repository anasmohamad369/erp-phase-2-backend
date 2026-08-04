package com.classroom.board.service;

import com.classroom.board.common.enums.LeaveStatus;
import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.LeaveRequestDto;
import com.classroom.board.entity.LeaveRequest;
import com.classroom.board.entity.Principal;
import com.classroom.board.entity.Teacher;
import com.classroom.board.repository.LeaveRequestRepository;
import com.classroom.board.repository.PrincipalRepository;
import com.classroom.board.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final TeacherRepository teacherRepository;
    private final PrincipalRepository principalRepository;

    @Transactional
    public LeaveRequestDto applyLeave(LeaveRequestDto dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Teacher", "id", dto.getTeacherId()));

        LeaveRequest leaveRequest = LeaveRequest.builder()
                .teacher(teacher)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .reason(dto.getReason())
                .status(LeaveStatus.PENDING)
                .build();

        LeaveRequest saved = leaveRequestRepository.save(leaveRequest);
        return mapToDto(saved);
    }

    @Transactional
    public LeaveRequestDto reviewLeave(Long leaveId, Long principalId, LeaveStatus status, String rejectionReason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest", "id", leaveId));
        Principal principal = principalRepository.findById(principalId)
                .orElseThrow(() -> new ResourceNotFoundException("Principal", "id", principalId));

        leaveRequest.setStatus(status);
        leaveRequest.setReviewedBy(principal);
        leaveRequest.setRejectionReason(rejectionReason);

        LeaveRequest updated = leaveRequestRepository.save(leaveRequest);
        return mapToDto(updated);
    }

    @Transactional(readOnly = true)
    public List<LeaveRequestDto> getTeacherLeaveRequests(Long teacherId) {
        return leaveRequestRepository.findByTeacherId(teacherId).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LeaveRequestDto> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus(LeaveStatus.PENDING).stream()
                .map(this::mapToDto)
                .toList();
    }

    public LeaveRequestDto mapToDto(LeaveRequest l) {
        return LeaveRequestDto.builder()
                .id(l.getId())
                .teacherId(l.getTeacher().getId())
                .teacherName(l.getTeacher().getUser().getFirstName() + " " + l.getTeacher().getUser().getLastName())
                .startDate(l.getStartDate())
                .endDate(l.getEndDate())
                .reason(l.getReason())
                .status(l.getStatus())
                .reviewedById(l.getReviewedBy() != null ? l.getReviewedBy().getId() : null)
                .reviewedByName(l.getReviewedBy() != null ? l.getReviewedBy().getUser().getFirstName() + " " + l.getReviewedBy().getUser().getLastName() : null)
                .rejectionReason(l.getRejectionReason())
                .build();
    }
}
