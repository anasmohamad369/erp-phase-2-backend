package com.classroom.board.service;

import com.classroom.board.config.JwtTokenProvider;
import com.classroom.board.common.exception.BadRequestException;
import com.classroom.board.common.exception.ResourceNotFoundException;
import com.classroom.board.dto.*;
import com.classroom.board.entity.Principal;
import com.classroom.board.entity.RefreshToken;
import com.classroom.board.entity.Teacher;
import com.classroom.board.entity.User;
import com.classroom.board.repository.PrincipalRepository;
import com.classroom.board.repository.RefreshTokenRepository;
import com.classroom.board.repository.TeacherRepository;
import com.classroom.board.repository.UserRepository;
import com.classroom.board.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;
    private final PrincipalRepository principalRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.refresh-token-expiration-ms:604800000}")
    private long refreshTokenExpirationMs;

    @Transactional
    public JwtResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        RefreshToken refreshToken = createRefreshToken(user);

        return JwtResponse.builder()
                .accessToken(jwt)
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    @Transactional
    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(refreshTokenExpirationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public JwtResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken token = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new BadRequestException("Refresh token is not in database!"));

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new BadRequestException("Refresh token was expired. Please make a new login request");
        }

        User user = token.getUser();
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal, null, userPrincipal.getAuthorities());

        String newAccessToken = tokenProvider.generateToken(authentication);

        return JwtResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(token.getToken())
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().name())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadRequestException("Current password does not match!");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void requestPasswordReset(PasswordResetRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));
        log.info("Password reset token generated for user {}", user.getUsername());
    }

    @Transactional(readOnly = true)
    public UserProfileDto getUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        UserProfileDto.UserProfileDtoBuilder builder = UserProfileDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .role(user.getRole().name());

        Optional<Teacher> teacherOpt = teacherRepository.findByUserId(userId);
        if (teacherOpt.isPresent()) {
            Teacher t = teacherOpt.get();
            builder.teacherId(t.getId())
                    .employeeId(t.getEmployeeId())
                    .designation(t.getDesignation())
                    .specialization(t.getSpecialization());
        }

        Optional<Principal> principalOpt = principalRepository.findByUserId(userId);
        if (principalOpt.isPresent()) {
            Principal p = principalOpt.get();
            builder.principalId(p.getId())
                    .qualification(p.getQualification())
                    .officeRoom(p.getOfficeRoom());
        }

        return builder.build();
    }
}
