import React from 'react';
import Login from './login';
import ReactDom from 'react-dom';
import Chat from './chat';
import {BrowserRouter} from 'react-router-dom';
import SignUp from './sign_up';
import PrivateRoute from './private_route';
import PublicRoute from './public_route';
import env from '../enviroment';

class App extends React.Component {

    static hasToken() {
        return localStorage.getItem('accessToken') == null;
    }

    render() {
        return (
            <BrowserRouter>
                <div>
                    <PublicRoute path={env.APP.LOGIN} component={Login}/>
                    <PublicRoute path={env.APP.REGISTRATION} component={SignUp}/>
                    <PrivateRoute path={env.APP.CHAT} component={Chat}/>
                </div>
            </BrowserRouter>
        )
    }
}

ReactDom.render(
    <App/>,
    document.getElementById('react')
);
export default App