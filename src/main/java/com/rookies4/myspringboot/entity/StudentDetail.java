package com.rookies4.myspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "student_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetail {
    // Primary Key (Sequential Value)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_detail_id")
    private Long id;

    // 주소
    @Column(nullable = false)
    private String address;

    // 전화번호
    @Column(nullable = false)
    private String phoneNumber;

    // 이메일주소
    @Column(unique = true, nullable = false)
    private String email;

    // 생년월일
    @Column
    private LocalDate dateOfBirth;

    // FK를 가진 엔티티가 주인 (Owner)이다.
    // @JoinColumn : foreign key에 홰당하는 어노테이션
    // 1:1 관계의 지연 로딩
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
