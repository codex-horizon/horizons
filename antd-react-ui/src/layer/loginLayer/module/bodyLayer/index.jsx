import Styles from './index.module.css';
import React, {} from 'react';
import {useNavigate} from 'react-router-dom';
import {Button, Form, Input, message} from 'antd';
import {UserOutlined, LockOutlined} from '@ant-design/icons';
import {userApi} from '../../../../api';

function FromLayer() {
    const [messageApi, contextHolder] = message.useMessage();
    const navigate = useNavigate();

    const onFinish = (values) => {
        userApi.sendAuthentication({}).then(res => {
            messageApi.open({
                type: 'success',
                content: '登录成功，即将跳转。',
                duration: 1,
                onClose: function () {
                    navigate('/index/user.html');
                }
            }).then(r => {
            });
        }).catch(e => {
        }).finally(() => {
        })
    };

    return (
        <Form className={Styles.fromLayer}
              name="login"
              style={{
                  maxWidth: 400,
              }}
              initialValues={{
                  username: '',
                  password: ''
              }}
              onFinish={onFinish}
              autoComplete="off"
        >
            <Form.Item name="username" rules={[
                {
                    required: true,
                    message: '请输入用户名！'
                },
            ]}
            ><Input placeholder="请输入用户名" prefix={<UserOutlined/>}/>
            </Form.Item>
            <Form.Item name="password" rules={[
                {
                    required: true,
                    message: '请输入密码！'
                },
            ]}
            ><Input.Password placeholder='请输入密码' prefix={<LockOutlined/>}/>
            </Form.Item>
            <Form.Item>
                {contextHolder}
                <Button type="primary" htmlType="submit" block>
                    登&#12288;录
                </Button>
            </Form.Item>
        </Form>
    );
}

export default function BodyLayer() {
    return (
        <div className={Styles.bodyLayer}>
            <FromLayer/>
        </div>
    );
};