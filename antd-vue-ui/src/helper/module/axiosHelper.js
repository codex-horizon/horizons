import axios, {} from 'axios';

const axiosInstance = axios.create({
    baseURL: './',
    headers: {},
    timeout: 1000 * 1.5,
    withCredentials: true
});

axiosInstance.interceptors.request.use((config) => {
    return Promise.resolve(config);
}, (error) => {
    return Promise.reject(error);
});
axiosInstance.interceptors.response.use((response) => {
    return Promise.resolve(response.data.body);
}, (error) => {
    return Promise.reject(error);
});

export const axiosHelper = (uri, method, headers, params, data, responseType) => {
    return new Promise((resolve, reject) => {
        axiosInstance({
            url: uri,
            method,
            headers,
            params,
            data,
            responseType, // stream 用于获取图片
            responseEncoding: 'utf8',
            xsrfCookieName: 'XSRF-TOKEN',
            xsrfHeaderName: 'X-XSRF-TOKEN',
        }).then(response => {
            resolve(response);
        }).catch(error => {
            reject(error);
        }).finally(() => {
            console.log('------------------ 可以做日记收集 ------------------')
        });
    });
};

export default axiosInstance;