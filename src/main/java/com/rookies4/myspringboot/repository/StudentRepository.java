package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // 학번으로 조회하는 Query Method
    Optional<Student> findByStudentNumber(String studentNumber);

    // Fetch join을 사용하여 1개의 Query만 생성되도록 처리함.
    @Query("SELECT s FROM Student s JOIN FETCH s.studentDetail WHERE s.id = :id")
    Optional<Student> findByIdWithStudentDetail(@Param("id") Long studentId);

    // 학번의 존재 여부
    boolean existsByStudentNumber(String studentNumber);
}
