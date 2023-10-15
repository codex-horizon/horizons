package com.later.horizon.common.helper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

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

    /**
     * 判断字符串是否阿拉伯数字
     * @param str 数字字符串
     * @return 布尔值
     */
    public static boolean isNumeric(String str) {
        return StringUtils.hasText(str) && Pattern.compile("[0-9]*").matcher(str.trim()).matches();
    }

}
