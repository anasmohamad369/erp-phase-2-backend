package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.PrincipalDto;
import com.classroom.board.entity.Principal;
import com.classroom.board.repository.PrincipalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PrincipalService {

    private final PrincipalRepository principalRepository;

    @Transactional(readOnly = true)
    public List<PrincipalDto> getAllPrincipals() {
        return principalRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public PrincipalDto getPrincipalById(Long id) {
        Principal principal = principalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Principal", "id", id));
        return mapToDto(principal);
    }

    public PrincipalDto mapToDto(Principal principal) {
        return PrincipalDto.builder()
                .id(principal.getId())
                .userId(principal.getUser().getId())
                .username(principal.getUser().getUsername())
                .email(principal.getUser().getEmail())
                .firstName(principal.getUser().getFirstName())
                .lastName(principal.getUser().getLastName())
                .qualification(principal.getQualification())
                .officeRoom(principal.getOfficeRoom())
                .build();
    }
}
