package com.classroom.board.repository;

import com.classroom.board.common.enums.LeaveStatus;
import com.classroom.board.entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByTeacherId(Long teacherId);
    List<LeaveRequest> findByStatus(LeaveStatus status);
}
