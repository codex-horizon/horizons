package com.later.horizon.common.helper;

import com.later.horizon.common.constants.Constants;
import com.later.horizon.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
public class RequestHelper {

    public static String getApplicationName() {
        return Optional.ofNullable(getHttpServletResponse().getHeader(Constants.Header_Application_Name)).orElse(Constants.Header_Application_Name_None);
    }

    public static String getTraceId() {
        return Optional.ofNullable(getHttpServletResponse().getHeader(Constants.Header_Trace_Id)).orElse(Constants.Header_Trace_Id_None);
    }

    public static String getRSAPublicKey() {
        return Optional.ofNullable(getHttpServletRequest().getHeader(Constants.Header_RSA_PublicKey)).orElseThrow(() -> new BusinessException(Constants.ProveProveState.Header_RSA_PublicKey_Non_Exist));
    }

    public static HttpSession getHttpSession(boolean create) {
        // getSession()相当于getSession(true)，参数为true时，若存在会话，则返回该会话，否则新建一个会话;
        // getSession(false)，参数为false时，如存在会话，则返回该会话，否则返回NULL;
        return Optional.ofNullable(getHttpServletRequest().getSession(create)).orElseThrow(() -> new BusinessException(Constants.ProveProveState.ServletRequest_Obtain_Failed));
    }

    public static HttpServletRequest getHttpServletRequest() {
        return Optional.of(getServletRequestAttributes().getRequest()).orElseThrow(() -> new BusinessException(Constants.ProveProveState.ServletRequest_Obtain_Failed));
    }

    public static HttpServletResponse getHttpServletResponse() {
        return Optional.ofNullable(getServletRequestAttributes().getResponse()).orElseThrow(() -> new BusinessException(Constants.ProveProveState.ServletRequest_Obtain_Failed));
    }

    private static ServletRequestAttributes getServletRequestAttributes() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).orElseThrow(() -> new BusinessException(Constants.ProveProveState.ServletRequest_Obtain_Failed));
    }

}

