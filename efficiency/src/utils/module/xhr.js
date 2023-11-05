'use strict';
import xhr from 'axios';
import {notification} from '@/utils';

export const nativeXhr = xhr.create({
    baseURL: process.env.VUE_APP_PROXY_URL,
    withCredentials: true,
    headers: {
    },
    timeout: 1000 * 2,
});
nativeXhr.interceptors.request.use(async (config) => {
    return config;
}, (error) => {
    return Promise.reject(error);
});
nativeXhr.interceptors.response.use((response) => {
    if (response.status === 200) {
        let {applicationName, traceId, body} = response.data;
        console.info(applicationName, traceId);
        return Promise.resolve(body);
    } else {
        return Promise.reject(response)
    }
}, (error) => {
    notification.error({
        title: `请求异常：${error.message}`,
        dangerouslyUseHTMLString: true,
        message: `接口：${error.config.method.toUpperCase()}<br>接口：${error.config.url}`
    });
    return Promise.reject(error);
});

export const get = (uri, params, data, headers) => {
    return new Promise((resolve, reject) => {
        nativeXhr({
            method: 'GET',
            url: uri,
            params,
            data,
            headers
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        }).finally(() => {
            console.log('~请求结束~')
        });
    });
};

export const post = (uri, params, data, headers) => {
    return new Promise((resolve, reject) => {
        nativeXhr({
            method: 'POST',
            url: uri,
            params,
            data,
            headers
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        }).finally(() => {
            console.log('~请求结束~')
        });
    });
};

export const put = (uri, params, data, headers) => {
    return new Promise((resolve, reject) => {
        nativeXhr({
            method: 'PUT',
            url: uri,
            params,
            data,
            headers
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        }).finally(() => {
            console.log('~请求结束~')
        });
    });
};

export const del = (uri, params, data, headers) => {
    return new Promise((resolve, reject) => {
        nativeXhr({
            method: 'DELETE',
            url: uri,
            params,
            data,
            headers
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        }).finally(() => {
            console.log('~请求结束~')
        });
    });
};

export {
    xhr
}