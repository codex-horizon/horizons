import {axiosHelper, requestMethod, preResponse} from '../../helper';

export const userApi = {
    sendAuthentication(data) {
        return axiosHelper(
            '/mock/loginLayer.JSON',
            requestMethod.GET,
            {
                'Content-Type': 'application/json;charset=utf-8'
            },
            {},
            data,
            preResponse.responseType.JSON
        );
    }
};