const dialogStore = {
    strict: process.env.NODE_ENV !== 'production',
    namespaced: true,
    state: {
        dialog: { // 后续改造Object.assign()
            containsViews: [],
            visible: false,
            title: '对话框',
            width: '36%',
            footer: true,
            fullScreen: false
        }
    },
    getters: {
        getDialogCurrentView: state => {
            return state.dialog.currentView;
        },
        getDialogVisible: state => {
            return state.dialog.visible;
        },
        getDialogTitle: state => {
            return state.dialog.title;
        },
        getDialogWidth: state => {
            return state.dialog.width;
        },
        getDialogFooter: state => {
            return state.dialog.footer;
        },
        getDialogFullScreen: state => {
            return state.dialog.fullScreen;
        }
    },
    mutations: {
        setDialogCurrentView(state, currentView) {
            state.dialog.currentView = currentView;
        },
        setDialogVisible(state, visible) {
            state.dialog.visible = visible;
        },
        setDialogTitle(state, title) {
            state.dialog.title = title;
        },
        setDialogWidth(state, width) {
            state.dialog.width = width;
        },
        setDialogFooter(state, footer) {
            state.dialog.footer = footer;
        },
        setDialogFullScreen(state, fullScreen) {
            state.dialog.fullScreen = fullScreen;
        }
    },
    actions: {}
};
export default dialogStore;