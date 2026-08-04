package com.classroom.board.entity;

import com.classroom.board.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Classroom extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false, unique = true, length = 20)
    private String roomNumber;

    @Column(length = 50)
    private String building;

    @Column(name = "digital_board_device_id", nullable = false, unique = true, length = 100)
    private String digitalBoardDeviceId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Builder.Default
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_class_id")
    private SchoolClass currentClass;
}
