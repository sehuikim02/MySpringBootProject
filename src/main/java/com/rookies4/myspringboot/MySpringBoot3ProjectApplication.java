package com.rookies4.myspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
// Application 클래스가 Contiguration(설정) 클래스 역할을 한다. : @SpringBootConfiguration
// 반드시 해당 클래스를 작성할 때, base-package 아래에 작성을 해야함. : @ComponentScan
// 외부 라이브러리에 대한 설정기능을 제공하는 AutoConfiguration 클래스를 활성화해주는 역할 : @EnableAutoConfiguration

public class MySpringBoot3ProjectApplication {

	public static void main(String[] args) {
//        SpringApplication.run(MySpringBoot3ProjectApplication.class, args);

        SpringApplication application = new SpringApplication(MySpringBoot3ProjectApplication.class);
        // application 타입을 변경하기
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.run(args);
    }

    @Bean
    public String hello() {
        return "Hello SpringBoot";
    }

}
