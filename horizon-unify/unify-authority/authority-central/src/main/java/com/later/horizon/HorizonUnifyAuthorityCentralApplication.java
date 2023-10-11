package com.later.horizon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@SpringBootApplication
@EnableJpaAuditing
public class HorizonUnifyAuthorityCentralApplication {

    private static final Logger Logger = LoggerFactory.getLogger(HorizonUnifyAuthorityCentralApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HorizonUnifyAuthorityCentralApplication.class, args);
    }

}