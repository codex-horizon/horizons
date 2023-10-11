package com.later.horizon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HorizonUnifyAuthorityCentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(HorizonUnifyAuthorityCentralApplication.class, args);
    }

}