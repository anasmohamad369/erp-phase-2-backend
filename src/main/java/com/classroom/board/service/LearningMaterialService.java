package com.classroom.board.service;

import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.entity.FileStorage;
import com.classroom.board.repository.FileStorageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningMaterialService {

    private final FileStorageRepository fileStorageRepository;

    @Transactional(readOnly = true)
    public List<FileStorage> getAllMaterials() {
        return fileStorageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public FileStorage getMaterialById(Long id) {
        return fileStorageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", id));
    }
}
