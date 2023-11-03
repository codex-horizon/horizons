'use strict';
import {post} from '@/utils';

export const userApi = {
    fetchAuthentication(data) {
        return post('/user/authentication', '', data, {});
    }
};