'use strict';
import {get} from '@/utils';

export const captchaApi = {
    fetchCaptcha(uniqueCode) {
        return get(`/captcha/create/${uniqueCode}`, '', '', {});
    },
};