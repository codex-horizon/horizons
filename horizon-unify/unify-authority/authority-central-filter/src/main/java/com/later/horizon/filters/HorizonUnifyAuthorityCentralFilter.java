package com.later.horizon.filters;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import javax.servlet.*;
import java.io.IOException;

@Slf4j
public class HorizonUnifyAuthorityCentralFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("in HorizonUnifyAuthorityCentralFilter init.");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("in HorizonUnifyAuthorityCentralFilter doFilter.");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        log.info("in HorizonUnifyAuthorityCentralFilter destroy.");
        Filter.super.destroy();
    }

}
