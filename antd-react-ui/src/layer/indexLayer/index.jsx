import Styles from './index.module.css';
import React, {} from 'react';
import {useNavigate, Link, Outlet} from 'react-router-dom';
import {Avatar, Dropdown, Space, Menu} from 'antd';
import {
    DownOutlined,
    UserOutlined,
    KeyOutlined,
    LogoutOutlined,
    PartitionOutlined,
    SettingOutlined,
    FundViewOutlined,
    DeploymentUnitOutlined,
    BugOutlined
} from '@ant-design/icons';
import avatarURI from '../../assets/avatar.gif';

export default function IndexView() {
    const logoURL = 'https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg';

    const dropdownItems = [{
        key: '0', label: '个人信息', icon: <UserOutlined/>
    }, {
        key: '1', label: '修改密码', icon: <KeyOutlined/>
    }, {
        type: 'divider',
    }, {
        key: '2', label: '自销账号', icon: <BugOutlined/>, disabled: true,
    }, {
        key: '3', label: '注销登录', icon: <LogoutOutlined/>
    },];

    return (<React.Fragment>
        <div className={Styles.headLayer}>
            <Link to="/index.html" className={Styles.titleLayer} style={{textDecoration: 'none'}}>
                <Avatar src={<img src={logoURL} alt={'avatar'}/>} size={36}/>
                <span className={Styles.title}>Antd React Pro</span>
            </Link>
            <Dropdown menu={{
                items: dropdownItems
            }}>
                <Space style={{cursor: 'pointer'}}>
                    <Avatar src={<img src={`${avatarURI}`} alt={'avatar'}/>} size={38}/>
                    <span className={Styles.avatar}>向晚门楣</span>
                    <DownOutlined/>
                </Space>
            </Dropdown>
        </div>
        <div className={Styles.containerLayer}>
            <div className={Styles.menuLayer}>
                <SideMenu/>
            </div>
            <div className={Styles.mainLayer}>
                <Outlet/>
            </div>
        </div>
    </React.Fragment>);
};

function SideMenu() {
    const navigate = useNavigate();

    const items = [{
        label: '0', key: '0', type: 'item', icon: <LogoutOutlined/>, children: [{
            label: <span style={{fontSize: '12px', color: '#1677ff'}}>用户级别，会破坏数据安全</span>,
            type: 'group',
            children: [{
                label: '0-0', key: 'l0', type: 'item', icon: <UserOutlined/>, children: [{
                    label: '0-0-0', key: 'l0_0.html', type: 'item', icon: <UserOutlined/>
                }, {
                    label: '0-0-1', key: 'l0_1.html', type: 'item', icon: <UserOutlined/>
                }]
            }]
        }],
    }, {
        label: '系统设置', key: '1', type: 'item', icon: <SettingOutlined/>, children: [{
            label: <span style={{fontSize: '12px', color: '#faad14'}}>系统级别，会破坏系统安全</span>,
            type: 'group',
            children: [{
                label: '用户管理', key: 'user.html', type: 'item', icon: <UserOutlined/>
            }, {
                label: '角色管理', key: 'role.html', type: 'item', icon: <DeploymentUnitOutlined/>
            }, {
                label: '菜单管理', key: 'menu.html', type: 'item', icon: <PartitionOutlined/>
            }, {
                label: '数据权限', key: 'authority.html', type: 'item', icon: <FundViewOutlined/>
            }]
        }],
    }];

    const onClick = (e) => {
        let path = e.keyPath.reverse();
        path[0] = 'index';
        navigate('/'.concat(path.join('/')), {replace: true})
    };

    return (<div style={{width: 256, height: '100%'}}>
        <Menu
            mode='inline'
            onClick={onClick}
            items={items}
            style={{height: '100%'}}/>
    </div>);
}