import Styles from './index.module.css';
import React, {} from 'react';
import {Avatar} from 'antd';

export default function TitleLayer() {
    const url = 'https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg';
    return (
        <div className={Styles.titleLayer}>
            <Avatar
                size={{xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100}}
                src={<img src={url} alt={'avatar'}/>}/>
            <span className={Styles.title}>
                <h1>Antd React Pro</h1>
            </span>
        </div>
    );
};