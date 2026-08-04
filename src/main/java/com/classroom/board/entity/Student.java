package com.classroom.board.entity;

import com.classroom.board.common.audit.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "students", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"roll_number", "class_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roll_number", nullable = false, length = 50)
    private String rollNumber;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "admission_number", nullable = false, unique = true, length = 50)
    private String admissionNumber;

    @Column(length = 10)
    private String gender;

    @Column(name = "hostel_block", length = 50)
    private String hostelBlock;

    @Column(name = "room_number", length = 20)
    private String roomNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private SchoolClass schoolClass;
}
