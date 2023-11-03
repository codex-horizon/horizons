'use strict';
import {useStore} from 'vuex';
import {createRouter, createWebHashHistory} from 'vue-router';

// ------------------------ 系统入口 ------------------------
const LoginView = () => import('@/assembly/before/LoginView.vue');
const IndexView = () => import('@/assembly/after/IndexView.vue');
const HomeView = () => import('@/assembly/after/HomeView.vue');

// ------------------------ 功能类别 ------------------------
const FuncCategoryView = () => import('@/assembly/after/funcCategory/FuncCategoryView.vue');
const ActiveUsersView = () => import('@/assembly/after/funcCategory/ActiveUsersView.vue');
const UserLotteryRecordsView = () => import('@/assembly/after/funcCategory/UserLotteryRecordsView.vue');
const CustomerServiceConfigView = () => import('@/assembly/after/funcCategory/CustomerServiceConfigView.vue');
const AreaCodeListView = () => import('@/assembly/after/funcCategory/AreaCodeListView.vue');
const UserManagementView = () => import('@/assembly/after/funcCategory/UserManagementView.vue');
const AuthorityManagementView = () => import('@/assembly/after/funcCategory/AuthorityManagementView.vue');

// ------------------------ 系统设置 ------------------------
const SystemManagementView = () => import('@/assembly/after/systemManagement/SystemManagementView.vue');
const UsersManagementView = () => import('@/assembly/after/systemManagement/UsersManagementView.vue');
const PlatformManagementView = () => import('@/assembly/after/systemManagement/PlatformManagementView.vue');
const MenuManagementView = () => import('@/assembly/after/systemManagement/MenuManagementView.vue');

// ------------------------ 系统设置 ------------------------
const PersonalView = () => import('@/assembly/after/personal/PersonalView.vue');
const PersonalInformationView = () => import('@/assembly/after/personal/PersonalInformationView.vue');
const PasswordModifyView = () => import('@/assembly/after/personal/PasswordModifyView.vue');

export const router = createRouter({
    strict: true,
    history: createWebHashHistory(process.env.VUE_APP_CONTEXT_PATH),
    routes: [
        {
            path: '/:pathMatch(.*)*',
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
                        title: '主页'
                    },
                    path: '/home.html',
                    name: HomeView.name,
                    component: HomeView
                }, {
                    meta: {
                        title: '功能类别'
                    },
                    path: '/funcCategory',
                    name: FuncCategoryView.name,
                    component: FuncCategoryView,
                    redirect: '/activeUsers.html',
                    children: [
                        {
                            meta: {
                                title: '活动用户'
                            },
                            path: '/activeUsers.html',
                            name: ActiveUsersView.name,
                            component: ActiveUsersView
                        }, {
                            meta: {
                                title: '用户抽奖记录'
                            },
                            path: '/userLotteryRecords.html',
                            name: UserLotteryRecordsView.name,
                            component: UserLotteryRecordsView
                        }, {
                            meta: {
                                title: '客服配置'
                            },
                            path: '/customerServiceConfig.html',
                            name: CustomerServiceConfigView.name,
                            component: CustomerServiceConfigView
                        }, {
                            meta: {
                                title: '区号列表'
                            },
                            path: '/areaCodeList.html',
                            name: AreaCodeListView.name,
                            component: AreaCodeListView
                        }, {
                            meta: {
                                title: '用户管理'
                            },
                            path: '/userManagement.html',
                            name: UserManagementView.name,
                            component: UserManagementView
                        }, {
                            meta: {
                                title: '权限管理'
                            },
                            path: '/authorityManagement.html',
                            name: AuthorityManagementView.name,
                            component: AuthorityManagementView
                        }
                    ]
                }, {
                    meta: {
                        title: '系统管理'
                    },
                    path: '/systemManagement',
                    name: SystemManagementView.name,
                    component: SystemManagementView,
                    redirect: '/usersManagement.html',
                    children: [{
                        meta: {
                            title: '用户管理'
                        },
                        path: '/usersManagement.html',
                        name: UsersManagementView.name,
                        component: UsersManagementView
                    }, {
                        meta: {
                            title: '平台管理'
                        },
                        path: '/platformManagement.html',
                        name: PlatformManagementView.name,
                        component: PlatformManagementView
                    }, {
                        meta: {
                            title: '菜单管理'
                        },
                        path: '/menuManagement.html',
                        name: MenuManagementView.name,
                        component: MenuManagementView
                    }]
                }, {
                    meta: {
                        title: '个人中心'
                    },
                    path: '/personal',
                    name: PersonalView.name,
                    component: PersonalView,
                    redirect: '/personalInformation.html',
                    children: [{
                        meta: {
                            title: '个人信息'
                        },
                        path: '/personalInformation.html',
                        name: PersonalInformationView.name,
                        component: PersonalInformationView
                    }, {
                        meta: {
                            title: '密码修改'
                        },
                        path: '/passwordModify.html',
                        name: PasswordModifyView.name,
                        component: PasswordModifyView
                    }]
                }
            ]
        }
    ]
});

/**
 * 全局前置守卫
 * Router.beforeEach 每次发生路由的导航跳转时，都会触发全局前置守卫，因此，在全局前置守卫中，程序员可以对每个路由进行访问权限的控制
 */
router.beforeEach(async (to, from, next) => {
    console.log('全局前置守卫', to, from, next());
});

/**
 * 全局解析守卫
 * Router.beforeResolve 是获取数据或执行任何其他操作（如果用户无法进入页面时你希望避免执行的操作）的理想位置
 */
router.beforeResolve(async (to, from, next) => {
    console.log('全局解析守卫', to, from, next());
});

/**
 * 全局后置守卫
 * Router.afterEach 对于分析、更改页面标题、声明页面等辅助功能以及许多其他事情都很有用。
 */
router.afterEach(async (to, from, failure) => {
    await useStore().dispatch('asyncSetBreadcrumbList', to.matched);
    console.log('全局后置守卫', to, from, failure);
});