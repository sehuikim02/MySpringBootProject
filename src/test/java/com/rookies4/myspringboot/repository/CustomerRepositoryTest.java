package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
// assertj 라이브러리의 Assertions 클래스 import
import static org.assertj.core.api.Assertions.assertThat;
// ctrl + shift + f10
@SpringBootTest
//  Test 성공해서 insert가 잘 되면, rollback 해서 Table에 data가 남지 않게 함
//@Transactional
class CustomerRepositoryTest {
    // Repository injection 받기
    @Autowired
    CustomerRepository customerRepository;

    @Test
//    @Rollback(value = false)    // Rollback 처리 X
    void testUpdateCustomer() {
        Customer customer =
                customerRepository.findByCustomerId("AC001")
                        .orElseThrow(() -> new RuntimeException("Customer Not Found"));     // update Query

        customer.setCustomerName("둘리2");    
        customerRepository.save(customer);
    }

    // 3. Customer 조회 - 존재하지 않으면 예외 발생
    @Test
    @Disabled
    void testNotFoundCustomer() {
        Customer notFoundCustomer =
                customerRepository.findByCustomerId("AC003")
                        .orElseThrow(() -> new RuntimeException("Customer Not Found"));
    }

    // 2. Customer 조회 - PK로 조회
    @Test
    @Disabled
    void testFindCustomer() {
        // 1. findById() 호출
        Optional<Customer> customerById = customerRepository.findById(1L);// id가 1인데 long타입이라 L을 붙여줘야함, query 문장 1
        assertThat(customerById).isNotEmpty();    // Testcase가 성공하려면 결과값이 True로 나와야하니까 isNotEmpty()
        if (customerById.isPresent()) { // 해당 id가 존재하면, isPresent()는 Optional 객체에 많이 사용
            Customer existCustomer = customerById.get();    // id를 가져와
            assertThat(existCustomer.getId()).isEqualTo(1L);    // 값을 비교
        }

        // Optional의 T, orElseGet(Supplier) => 해당 고객번호(AC001)가 존재하는지 확인
        // Supplier 추상메서드
        Optional<Customer> customerByCustomerId = customerRepository.findByCustomerId("AC001"); // query 문장 2
        Customer ac001Customer = customerByCustomerId.orElseGet(() -> new Customer());
        assertThat(ac001Customer.getCustomerName()).isEqualTo("홍길동");

        // 고객번호 (AC003)이 존재하지 않는 경우
        Customer notFoundCustomer =
                customerRepository.findByCustomerId("AC003").orElseGet(() -> new Customer());   // query 문장 3
        assertThat(notFoundCustomer.getCustomerName()).isNull();
    }

    
    // 1. Customer 등록
    @Test
    @Transactional  // 개별 메소드만 Transaction 설정 O
    @Rollback(value = false)    // Rollback 처리를 막음 (false)
//    @Disabled
    void testSaveCustomer() {
        // 1. Given (준비단계)
        Customer customer = new Customer();
        customer.setCustomerId("AC003");    // 고객번호
        customer.setCustomerName("스프링FW");    // 고객이름

        // 2. When (실행단계) - DB 저장
        Customer savedCustomer = customerRepository.save(customer);

        // Then (검증단계)
        // assertEquals(expected, actual)
        // 등록된 Customer Enitity 객체가 Null이 아닌지 검증
        assertThat(savedCustomer).isNotNull();
        // 등록된 Customer Name의 값이 동일한지 검증하기
        assertThat(savedCustomer.getCustomerName()).isEqualTo("스프링FW");
    }
}