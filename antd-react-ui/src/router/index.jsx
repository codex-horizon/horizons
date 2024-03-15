import React, {Suspense} from 'react';
import {
    createBrowserRouter, /*createHashRouter,
    createRoutesFromElements,
    Route,*/
    Navigate
} from 'react-router-dom';
import Styles from './../index.module.css';

const LoginView = React.lazy(() => import('../layer/loginLayer/index.jsx'));
const IndexView = React.lazy(() => import('../layer/indexLayer/index.jsx'));

const VideoCategoryView = React.lazy(() => import('../layer/indexLayer/level/videoCategoryLayer/index.jsx'));
const WallpaperCategoryView = React.lazy(() => import('../layer/indexLayer/level/wallpaperCategoryLayer/index.jsx'));

const UserView = React.lazy(() => import('../layer/indexLayer/module/userLayer/index.jsx'));
const RoleView = React.lazy(() => import('../layer/indexLayer/module/roleLayer/index.jsx'));
const MenuView = React.lazy(() => import('../layer/indexLayer/module/menuLayer/index.jsx'));
const AuthorityView = React.lazy(() => import('../layer/indexLayer/module/authorityLayer/index.jsx'));
const ErrorView = React.lazy(() => import('../layer/errorLayer/index.jsx'));
const NotFoundView = React.lazy(() => import('../layer/notFoundLayer/index.jsx'));
// const ViewRouter = createBrowserRouter(createRoutesFromElements(
//     <Route>
//         <Route path='/' element={<Navigate to='/login.html'/>}/>,
//         <Route path='/login.html' element={<LoginView/>}/>
//         <Route path='/index.html' element={<IndexView/>}/>
//     </Route>
// ), {basename: '/'});
const contactLoader = () => {

};

const contactAction = () => {

}

const lazyLoading = (childNodes) => {
    return <Suspense fallback={
        <div className={Styles.loadingLayer}>
            <h1>🌀 加载中...</h1>
        </div>}
    >
        {childNodes}
    </Suspense>
}

const routes = [{
    path: '/',
    loader: contactLoader(),
    action: contactAction(),
    element: <Navigate to='/login.html'/>,
    errorElement: <ErrorView/>
}, {
    path: '/login.html',
    loader: contactLoader(),
    action: contactAction(),
    element: <LoginView/>,
    errorElement: <ErrorView/>
}, {
    path: '/index',
    loader: contactLoader(),
    action: contactAction(),
    element: <IndexView/>,
    errorElement: <ErrorView/>,
    children: [// 业务级
        {
            path: 'siteCategory', loader: contactLoader(), action: contactAction(), children: [{
                path: 'videoCategory.html',
                loader: contactLoader(),
                action: contactAction(),
                element: lazyLoading(<VideoCategoryView/>),
                errorElement: <ErrorView/>
            }, {
                path: 'wallpaperCategory.html',
                loader: contactLoader(),
                action: contactAction(),
                element: lazyLoading(<WallpaperCategoryView/>),
                errorElement: <ErrorView/>
            }]
        }, // 系统级
        {
            path: 'user.html',
            loader: contactLoader(),
            action: contactAction(),
            element: lazyLoading(<UserView/>),
            errorElement: <ErrorView/>
        }, {
            path: 'role.html',
            loader: contactLoader(),
            action: contactAction(),
            element: lazyLoading(<RoleView/>),
            errorElement: <ErrorView/>
        }, {
            path: 'menu.html',
            loader: contactLoader(),
            action: contactAction(),
            element: lazyLoading(<MenuView/>),
            errorElement: <ErrorView/>
        }, {
            path: 'authority.html',
            loader: contactLoader(),
            action: contactAction(),
            element: lazyLoading(<AuthorityView/>),
            errorElement: <ErrorView/>
        }]
}, {
    path: '*', loader: contactLoader(), action: contactAction(), element: <NotFoundView/>, errorElement: <ErrorView/>
},];

const ViewRouter = createBrowserRouter(routes, {basename: '/'});

export default ViewRouter;
