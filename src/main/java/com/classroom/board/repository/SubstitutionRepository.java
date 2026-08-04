package com.classroom.board.repository;

import com.classroom.board.entity.Substitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SubstitutionRepository extends JpaRepository<Substitution, Long> {
    List<Substitution> findBySubstituteTeacherIdAndSubstitutionDate(Long substituteTeacherId, LocalDate date);
    List<Substitution> findByOriginalTeacherIdAndSubstitutionDate(Long originalTeacherId, LocalDate date);
    List<Substitution> findBySubstitutionDate(LocalDate date);
}
