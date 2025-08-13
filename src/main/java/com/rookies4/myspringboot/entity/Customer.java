package com.rookies4.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter @Setter
public class Customer {
    // PK, PK 값을 Persistence Provider가 결정해라 라는 의미
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Unique한 값을 가져야하고, Null 값을 허용하지 않는다.
    @Column(unique = true, nullable = false)
    private String customerId;

    // Null 값을 허용하지 않는다.
    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createAt = LocalDateTime.now();
}
