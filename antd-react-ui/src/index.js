import React, {} from 'react';
import ReactDOM from 'react-dom/client';
import Styles from './index.module.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <React.Suspense fallback={<Loading/>}>
            <App/>
        </React.Suspense>
    </React.StrictMode>
);

function Loading() {
    return (
        <div className={Styles.loadingLayer}>
            <h1>🌀 加载中...</h1>
        </div>
    );
}

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();