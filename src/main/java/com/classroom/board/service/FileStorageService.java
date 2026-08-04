package com.classroom.board.service;

import com.classroom.board.common.exception.BadRequestException;
import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.entity.FileStorage;
import com.classroom.board.entity.User;
import com.classroom.board.repository.FileStorageRepository;
import com.classroom.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${file.upload-dir:./uploads}")
    private String uploadDir;

    private final FileStorageRepository fileStorageRepository;
    private final UserRepository userRepository;

    @Transactional
    public FileStorage storeFile(MultipartFile file, Long uploaderUserId) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (originalFileName.contains("..")) {
            throw new BadRequestException("Filename contains invalid path sequence " + originalFileName);
        }

        User uploader = userRepository.findById(uploaderUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", uploaderUserId));

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String storedFileName = UUID.randomUUID() + "_" + originalFileName;
            Path targetLocation = uploadPath.resolve(storedFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            FileStorage fileStorage = FileStorage.builder()
                    .fileName(originalFileName)
                    .storedName(storedFileName)
                    .filePath(targetLocation.toString())
                    .contentType(file.getContentType() != null ? file.getContentType() : "application/octet-stream")
                    .sizeBytes(file.getSize())
                    .uploader(uploader)
                    .build();

            return fileStorageRepository.save(fileStorage);
        } catch (IOException ex) {
            log.error("Could not store file {}", originalFileName, ex);
            throw new BadRequestException("Could not store file " + originalFileName + ". Please try again!");
        }
    }

    public Resource loadFileAsResource(Long fileId) {
        FileStorage fileStorage = fileStorageRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File", "id", fileId));

        try {
            Path filePath = Paths.get(fileStorage.getFilePath()).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found on disk " + fileStorage.getFileName());
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found " + fileStorage.getFileName());
        }
    }

    public FileStorage getFileMetadata(Long fileId) {
        return fileStorageRepository.findById(fileId)
                .orElseThrow(() -> new ResourceNotFoundException("File", "id", fileId));
    }
}
