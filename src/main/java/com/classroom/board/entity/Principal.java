package com.classroom.board.entity;

import com.classroom.board.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "principals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Principal extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 100)
    private String qualification;

    @Column(name = "office_room", length = 20)
    private String officeRoom;
}
