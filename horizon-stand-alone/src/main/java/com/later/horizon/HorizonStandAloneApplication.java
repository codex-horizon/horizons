package com.later.horizon;

import com.later.horizon.common.helper.BeanHelper;
import com.later.horizon.core.configurer.security.EnvironmentPropertyContextInitializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class HorizonStandAloneApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(HorizonStandAloneApplication.class);
        springApplication.addInitializers(new EnvironmentPropertyContextInitializer());
        BeanHelper.setApplicationContext(springApplication.run(args));
    }

}
