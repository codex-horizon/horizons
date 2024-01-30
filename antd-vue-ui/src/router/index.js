'use strict';
import {createRouter, createWebHashHistory} from 'vue-router';
// import {useStore} from 'vuex';
const LoginView = () => import('../layer/loginLayer/index.vue');
const IndexView = () => import('../layer/indexLayer/index.vue');

const Level0_0_0View = () => import('../layer/indexLayer/level/0_0_0.vue');
const Level0_1_0View = () => import('../layer/indexLayer/level/0_1_0.vue');

const UserView = () => import('../layer/indexLayer/module/userLayer/index.vue');
const RoleView = () => import('../layer/indexLayer/module/roleLayer/index.vue');
const MenuView = () => import('../layer/indexLayer/module/menuLayer/index.vue');
const AuthorityView = () => import('../layer/indexLayer/module/authorityLayer/index.vue');
const ErrorView = () => import('../layer/errorLayer/index.vue');

const router = createRouter({
    strict: true,
    history: createWebHashHistory(process.env.VUE_APP_CONTEXT_PATH),
    routes: [
        {
            path: '/',
            redirect: '/login.html'
        }, {
            meta: {
                title: '登录'
            },
            path: '/login.html',
            name: LoginView.name,
            component: LoginView
        }, {
            meta: {
                title: '首页'
            },
            path: '/index',
            name: IndexView.name,
            component: IndexView,
            redirect: '/home.html',
            children: [
                {
                    meta: {
                        title: 'l0'
                    },
                    path: 'l0_0.html',
                    name: Level0_0_0View.name,
                    component: Level0_0_0View
                },
                {
                    meta: {
                        title: 'l1'
                    },
                    path: 'l0_1.html',
                    name: Level0_1_0View.name,
                    component: Level0_1_0View
                },
                {
                    meta: {
                        title: 'user'
                    },
                    path: 'user.html',
                    name: UserView.name,
                    component: UserView
                },
                {
                    meta: {
                        title: 'role'
                    },
                    path: 'role.html',
                    name: RoleView.name,
                    component: RoleView
                },
                {
                    meta: {
                        title: 'menu'
                    },
                    path: 'menu.html',
                    name: MenuView.name,
                    component: MenuView
                },
                {
                    meta: {
                        title: 'authority'
                    },
                    path: 'authority.html',
                    name: AuthorityView.name,
                    component: AuthorityView
                }
            ]
        }, {
            meta: {
                title: '错误'
            },
            path: '/error.html',
            name: ErrorView.name,
            component: ErrorView
        },
    ]
});

/**
 * 全局前置守卫
 * Router.beforeEach 每次发生路由的导航跳转时，都会触发全局前置守卫，因此，在全局前置守卫中，程序员可以对每个路由进行访问权限的控制
 */
router.beforeEach((to, from, next) => {
    console.log('全局前置守卫', to, from, next());
});

/**
 * 全局解析守卫
 * Router.beforeResolve 是获取数据或执行任何其他操作（如果用户无法进入页面时你希望避免执行的操作）的理想位置
 */
router.beforeResolve((to, from, next) => {
    console.log('全局解析守卫', to, from, next());
});

/**
 * 全局后置守卫
 * Router.afterEach 对于分析、更改页面标题、声明页面等辅助功能以及许多其他事情都很有用。
 */
router.afterEach((to, from, failure) => {
    // await useStore().dispatch('asyncSetBreadcrumbs', to.matched);
    console.log('全局后置守卫', to, from, failure);
});

export default router;