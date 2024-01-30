'use strict';
import {createStore} from 'vuex';
import dialogStore from './module/dialog.js';

export default createStore({
    strict: process.env.NODE_ENV !== 'production',
    namespaced: true,
    state() {
        return {
            menu: []
        }
    },
    getters: {
        fetchMenu(state) {
            return state.menu;
        }
    },
    mutations: {
        sendMenu(state, menu) {
            state.menu = menu;
        }
    },
    actions: {
        asyncSendMenu(context, menu) {
            context.commit('sendMenu', menu);
        }/*,
        asyncSetBreadcrumbs({commit}, breadcrumbs) {
            // const filteredBreadcrumbs = breadcrumbs.filter(breadcrumb => {
            //     return breadcrumb.redirect === undefined;
            // });
            commit('setBreadcrumbs', breadcrumbs);
        },
        asyncSetIcons(context, icons) {
            context.commit('setIcons', icons);
        },*/
    },
    modules: {
        dialogStore
    }
});