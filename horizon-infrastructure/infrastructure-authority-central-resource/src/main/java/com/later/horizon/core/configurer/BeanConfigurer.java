package com.later.horizon.core.configurer;

import com.later.horizon.common.converter.Converter;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.core.filters.TraceIdCorsFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Configuration
public class BeanConfigurer {

    private final ValuesConfigurer valuesConfigurer;

    BeanConfigurer(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(UserDetails.class::cast)
                .map(UserDetails::getUsername);
    }

    @Bean
    public IConverter iConverter() {
        return new Converter();
    }

    @Bean
    public FilterRegistrationBean<TraceIdCorsFilter> traceIdCorsFilter() {
        return new FilterRegistrationBean<TraceIdCorsFilter>() {{
            setFilter(new TraceIdCorsFilter(valuesConfigurer));
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Collections.singletonList("/*"));
        }};
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return NoOpPasswordEncoder.getInstance();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
