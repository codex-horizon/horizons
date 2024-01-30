import React, {} from 'react';
import {
    createBrowserRouter, /*createHashRouter,
    createRoutesFromElements,
    Route,*/
    Navigate
} from 'react-router-dom';

const LoginView = React.lazy(() => import('../layer/loginLayer/index.jsx'));
const IndexView = React.lazy(() => import('../layer/indexLayer/index.jsx'));

const L0View = React.lazy(() => import('../layer/indexLayer/level/0_0_0.jsx'));
const L1View = React.lazy(() => import('../layer/indexLayer/level/0_1_0.jsx'));

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
            path: 'l0', loader: contactLoader(), action: contactAction(), children: [{
                path: 'l0_0.html',
                loader: contactLoader(),
                action: contactAction(),
                element: <L0View/>,
                errorElement: <ErrorView/>
            }, {
                path: 'l0_1.html',
                loader: contactLoader(),
                action: contactAction(),
                element: <L1View/>,
                errorElement: <ErrorView/>
            }]
        }, // 系统级
        {
            path: 'user.html',
            loader: contactLoader(),
            action: contactAction(),
            element: <UserView/>,
            errorElement: <ErrorView/>
        }, {
            path: 'role.html',
            loader: contactLoader(),
            action: contactAction(),
            element: <RoleView/>,
            errorElement: <ErrorView/>
        }, {
            path: 'menu.html',
            loader: contactLoader(),
            action: contactAction(),
            element: <MenuView/>,
            errorElement: <ErrorView/>
        }, {
            path: 'authority.html',
            loader: contactLoader(),
            action: contactAction(),
            element: <AuthorityView/>,
            errorElement: <ErrorView/>
        }]
}, {
    path: '*', loader: contactLoader(), action: contactAction(), element: <NotFoundView/>, errorElement: <ErrorView/>
},];

const ViewRouter = createBrowserRouter(routes, {basename: '/'});

export default ViewRouter;
