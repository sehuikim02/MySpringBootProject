### [실습 2-4] Spring Boot와 JPA(Java Persistence API) 활용

* Student와 StudentDetail 1:1 (OneToOne) entity 연관관계
* Student와 Department 1:N (OneToMany) 엔티티 연관관계
    * FetchType.LAZY vs FetchType.EAGER
    * @JoinColumn, mappedBy
    * 연관관계의 주인 (owner와 종속 (non-owner))
        * Owner (BookDetail), Non-Owner(Book)
* DTO (Data Transfer Object)
* Service
* Controller
* Repository
* DataInsertRunner
* N+1 문제 해결
  * 성능 개선
  * HibernateModule 사용 BatchSize 설정하기