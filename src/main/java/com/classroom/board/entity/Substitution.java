package com.classroom.board.entity;

import com.classroom.board.common.audit.BaseAuditEntity;
import com.classroom.board.common.enums.SubstitutionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "substitutions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Substitution extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_request_id")
    private LeaveRequest leaveRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timetable_id", nullable = false)
    private TimetableEntry timetableEntry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_teacher_id", nullable = false)
    private Teacher originalTeacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "substitute_teacher_id", nullable = false)
    private Teacher substituteTeacher;

    @Column(name = "substitution_date", nullable = false)
    private LocalDate substitutionDate;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false, length = 20)
    private SubstitutionStatus status = SubstitutionStatus.ASSIGNED;
}
