package com.classroom.board.entity;

import com.classroom.board.common.audit.BaseAuditEntity;
import com.classroom.board.common.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "announcements")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Announcement extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_role", length = 30)
    private Role targetRole;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_class_id")
    private SchoolClass targetClass;

    @Builder.Default
    @Column(name = "is_urgent", nullable = false)
    private Boolean isUrgent = false;
}
