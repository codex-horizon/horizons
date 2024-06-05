package com.later.horizon.common.helper;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class CommonHelper {

    private static final AntPathMatcher AntPathMatcher = new AntPathMatcher();

    public static boolean matchURI(String URI, String... URIs) {
        for (String uri : URIs) {
            if (AntPathMatcher.match(uri, URI)) {
                return true;
            }
        }
        return false;
    }

    public static Matcher regex(String expression, String str) {
        return Pattern.compile(expression).matcher(str);
    }

    public static String findRegex(String expression, String str) {
        Matcher matcher = regex(expression, str);
        return matcher.find() ? matcher.group() : "";
    }

    public static boolean matchRegex(String expression, String str) {
        return regex(expression, str).find();
    }

    public static boolean hasNumeric(String str) {
        return StringUtils.hasText(str) && Pattern.compile("[0-9]*").matcher(str.trim()).matches();
    }

    public static String createUUID() {
        return UUID.randomUUID() + "-" + DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
    }

    public static Map<String, String> createCaptcha() {
        Captcha captcha = new SpecCaptcha(150, 38, 5);
        Map<String, String> result = new HashMap<>();
        result.put(captcha.text(), captcha.toBase64());
        return result;
    }

}
