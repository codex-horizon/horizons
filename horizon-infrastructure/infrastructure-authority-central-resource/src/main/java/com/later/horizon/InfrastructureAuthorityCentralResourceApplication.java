package com.later.horizon;

import com.later.horizon.common.helper.BeanHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class InfrastructureAuthorityCentralResourceApplication {

    public static void main(String[] args) {
        BeanHelper.setApplicationContext(SpringApplication.run(InfrastructureAuthorityCentralResourceApplication.class, args));
    }

}