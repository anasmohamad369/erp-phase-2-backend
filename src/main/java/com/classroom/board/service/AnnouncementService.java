package com.classroom.board.service;

import com.classroom.board.common.enums.Role;
import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.AnnouncementDto;
import com.classroom.board.entity.Announcement;
import com.classroom.board.entity.SchoolClass;
import com.classroom.board.entity.User;
import com.classroom.board.repository.AnnouncementRepository;
import com.classroom.board.repository.SchoolClassRepository;
import com.classroom.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;

    @Transactional
    public AnnouncementDto createAnnouncement(AnnouncementDto dto) {
        User author = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", dto.getAuthorId()));

        SchoolClass targetClass = null;
        if (dto.getTargetClassId() != null) {
            targetClass = schoolClassRepository.findById(dto.getTargetClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class", "id", dto.getTargetClassId()));
        }

        Announcement announcement = Announcement.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .targetRole(dto.getTargetRole())
                .targetClass(targetClass)
                .isUrgent(dto.getIsUrgent() != null ? dto.getIsUrgent() : false)
                .build();

        Announcement saved = announcementRepository.save(announcement);
        return mapToDto(saved);
    }

    @Transactional(readOnly = true)
    public List<AnnouncementDto> getAnnouncementsForRole(Role role) {
        return announcementRepository.findByTargetRoleOrTargetRoleIsNullOrderByCreatedAtDesc(role).stream()
                .map(this::mapToDto)
                .toList();
    }

    public AnnouncementDto mapToDto(Announcement a) {
        return AnnouncementDto.builder()
                .id(a.getId())
                .title(a.getTitle())
                .content(a.getContent())
                .authorId(a.getAuthor().getId())
                .authorName(a.getAuthor().getFirstName() + " " + a.getAuthor().getLastName())
                .targetRole(a.getTargetRole())
                .targetClassId(a.getTargetClass() != null ? a.getTargetClass().getId() : null)
                .targetClassName(a.getTargetClass() != null ? a.getTargetClass().getGrade() + "-" + a.getTargetClass().getSection() : "ALL")
                .isUrgent(a.getIsUrgent())
                .createdAt(a.getCreatedAt())
                .build();
    }
}
