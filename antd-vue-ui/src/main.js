import {createApp} from 'vue';
import Antd from 'ant-design-vue';
import App from './App';
import 'ant-design-vue/dist/reset.css';
import Store from './store/index.js';
import Router from './router/index.js';

const app = createApp(App);
app.use(Antd).use(Store).use(Router).mount('#app');
