package com.classroom.board.entity;

import com.classroom.board.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "teacher_diary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDiary extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "period_number", nullable = false)
    private Integer periodNumber;

    @Column(name = "topics_covered", nullable = false, columnDefinition = "TEXT")
    private String topicsCovered;

    @Column(name = "teaching_methodology", columnDefinition = "TEXT")
    private String teachingMethodology;

    @Column(name = "reflection_notes", columnDefinition = "TEXT")
    private String reflectionNotes;

    @Column(name = "board_state_summary", columnDefinition = "TEXT")
    private String boardStateSummary;
}
