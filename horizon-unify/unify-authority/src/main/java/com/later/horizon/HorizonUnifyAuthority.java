package com.later.horizon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HorizonUnifyAuthority {

    public static void main(String[] args) {
        SpringApplication.run(HorizonUnifyAuthority.class, args);
    }

}
