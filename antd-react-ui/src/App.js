import {RouterProvider} from 'react-router-dom';
import ViewRouter from './router';
import './App.css';

export default function App() {
    return (
        <RouterProvider router={ViewRouter}/>
    );
};