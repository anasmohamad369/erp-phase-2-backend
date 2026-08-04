package com.classroom.board.repository;

import com.classroom.board.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByRoomNumber(String roomNumber);
    Optional<Classroom> findByDigitalBoardDeviceId(String digitalBoardDeviceId);
}
