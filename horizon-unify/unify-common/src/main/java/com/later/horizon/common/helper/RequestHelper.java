package com.later.horizon.common.helper;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class RequestHelper {

    public static String createTraceId() {
        return UUID.randomUUID() + "-" + Instant.now().getEpochSecond();
    }

    public static String getApplicationNameByResponse() {
        return Optional.ofNullable(getHttpServletResponse().getHeader(Constants.Header_Key_Application_Name)).orElse(Constants.Header_Application_Name_None);
    }

    public static String getApplicationNameByResponse(HttpServletResponse response) {
        return Optional.ofNullable(response.getHeader(Constants.Header_Key_Application_Name)).orElse(Constants.Header_Application_Name_None);
    }

    public static String getTraceIdByRequest() {
        return Optional.ofNullable(getHttpServletResponse().getHeader(Constants.Header_Trace_Id)).orElse(Constants.Header_Trace_Id_None);
    }

    public static String getTraceIdByResponse(HttpServletResponse response) {
        return Optional.ofNullable(response.getHeader(Constants.Header_Trace_Id)).orElse(Constants.Header_Trace_Id_None);
    }

    public static String getPublicKeyByRequest() {
        return Optional.ofNullable(getHttpServletRequest().getHeader(Constants.Header_Key_Rsa_Public_Key)).orElseThrow(() -> new BizException(Constants.BizStatus.Request_Header_PublicKey_Non_Exist));
    }

    public static HttpServletRequest getHttpServletRequest() {
        return Optional.of(getServletRequestAttributes().getRequest()).orElseThrow(() -> new BizException(Constants.BizStatus.Request_Obtain_Fail));
    }

    public static HttpServletResponse getHttpServletResponse() {
        return Optional.ofNullable(getServletRequestAttributes().getResponse()).orElseThrow(() -> new BizException(Constants.BizStatus.Response_Obtain_Fail));
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).orElseThrow(() -> new BizException(Constants.BizStatus.ServletRequestAttributes_Obtain_Fail));
    }

    public static String getValueByParameterName(String parameterName) {
        return getHttpServletRequest().getParameter(parameterName);
    }

}

