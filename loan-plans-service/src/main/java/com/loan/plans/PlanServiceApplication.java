package com.loan.plans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.loan.plans.*"})
@EnableJpaRepositories("com.loan.library.*")
@EntityScan("com.loan.library.*")
public class PlanServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PlanServiceApplication.class, args);
    }
}
