import Styles from './index.module.css';
import {SmileTwoTone} from '@ant-design/icons';

export default function FooterLayer() {
    return (
        <div className={Styles.footerLayer}>
            <span>
            Ant Design UI
            </span>
            <span>
            <SmileTwoTone/> Powered by Antd React
            </span>
        </div>
    );
};