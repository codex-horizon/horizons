'use strict';
import {createApp} from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
import '@/theme/index.scss';
import * as ElementPlusIcons from '@element-plus/icons-vue';
import App from './App.vue';
import {store} from '@/store/index.js';
import {router} from '@/router/index.js';

const app = createApp(App);
app.use(router).use(store).use(ElementPlus);
for (const [key, component] of Object.entries(ElementPlusIcons)) {
    app.component(key, component)
}
app.mount('#app');