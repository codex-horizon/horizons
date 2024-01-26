package com.later.horizon.core.configurer;

import com.later.horizon.common.converter.Converter;
import com.later.horizon.common.converter.IConverter;
import com.later.horizon.core.filters.CorsFilter;
import com.later.horizon.core.filters.TraceFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.Collections;

@Slf4j
@Configuration
public class BeanConfigurer {

    private final CommonConfigurer commonConfigurer;

    BeanConfigurer(final CommonConfigurer commonConfigurer) {
        this.commonConfigurer = commonConfigurer;
    }

    /*@Bean
    public AuditorAware<UserDetails> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(User.class::cast)
                *//*.map(User::getUsername)*//*;
    }*/

    @Bean
    public IConverter iConverter() {
        return new Converter();
    }

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        return new FilterRegistrationBean<TraceFilter>() {{
            setFilter(new TraceFilter(commonConfigurer));
            setOrder(Ordered.HIGHEST_PRECEDENCE);
            setUrlPatterns(Collections.singletonList("/*"));
        }};
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        return new FilterRegistrationBean<CorsFilter>() {{
            setFilter(new CorsFilter(commonConfigurer));
            setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
            setUrlPatterns(Collections.singletonList("/*"));
        }};
    }

}
