package com.aline.bankmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.aline")
@EnableJpaRepositories(basePackages = "com.aline.core.repository")
@EntityScan(basePackages = "com.aline.core.model")
public class BankMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankMicroserviceApplication.class, args);
    }

}
