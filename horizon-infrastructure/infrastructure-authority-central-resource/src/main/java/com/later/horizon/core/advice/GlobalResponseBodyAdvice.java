package com.later.horizon.core.advice;

import com.later.horizon.common.helper.RequestHelper;
import com.later.horizon.common.restful.IGlobalResponse;
import com.later.horizon.core.configurer.ValuesConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private final ValuesConfigurer valuesConfigurer;

    public GlobalResponseBodyAdvice(final ValuesConfigurer valuesConfigurer) {
        this.valuesConfigurer = valuesConfigurer;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return Boolean.TRUE;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return IGlobalResponse.GlobalResponse.restful(valuesConfigurer.getApplicationName(), RequestHelper.getTraceId(), body);
    }
}