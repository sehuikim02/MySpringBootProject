package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 1. CustomerID로 조회하는 Query 메소드 만들기
    Optional<Customer> findByCustomerId(String customerId);

    // 2. CustomerName로 조회하는 Query 메소드 만들기
    List<Customer> findByCustomerNameContains(String customerName);
}
