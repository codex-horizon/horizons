package com.later.horizon.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import java.io.IOException;

public class HorizonUnifyAuthorityCentralFilter implements Filter {

    private static final Logger Logger = LoggerFactory.getLogger(HorizonUnifyAuthorityCentralFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (Logger.isDebugEnabled()) {
            Logger.debug("init horizon-unify filter.");
        }
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (Logger.isDebugEnabled()) {
            Logger.debug("in horizon-unify filter.");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        if (Logger.isDebugEnabled()) {
            Logger.debug("destroy horizon-unify filter.");
        }
        Filter.super.destroy();
    }
}
