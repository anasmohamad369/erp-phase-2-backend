package com.classroom.board.repository;

import com.classroom.board.common.enums.Role;
import com.classroom.board.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByTargetRoleOrTargetRoleIsNullOrderByCreatedAtDesc(Role targetRole);
    List<Announcement> findByTargetClassIdOrderByCreatedAtDesc(Long targetClassId);
}
