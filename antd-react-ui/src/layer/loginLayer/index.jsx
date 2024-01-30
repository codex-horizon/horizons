import Styles from './index.module.css';
import React, {} from 'react';

const TitleLayer = React.lazy(() => import('./module/titleLayer'));
const BodyLayer = React.lazy(() => import('./module/bodyLayer'));
const FooterLayer = React.lazy(() => import('./module/footerLayer'));

export default function LoginView() {
    return (
        <div className={Styles.containerLayer}>
            {/* TitleLayer */}
            <TitleLayer/>

            {/* BodyLayer */}
            <BodyLayer/>

            {/* FooterLayer */}
            <FooterLayer/>
        </div>
    );
};