package com.later.horizon.common.helper;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;

public class CaptchaHelper {

    public static String create(String uuid) {
        Captcha captcha = new SpecCaptcha(150, 38, 5);
//        Map<String, String> result = new HashMap<>();
//        result.put(captcha.text(), captcha.toBase64());
        return captcha.toBase64();
    }

}
