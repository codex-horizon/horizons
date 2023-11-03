'use strict';
import {post} from '@/utils';

export const menuApi = {
    fetchPageable(data, headers) {
        return post('/menu/fetchPageable', {}, data, headers);
    }
};