import React from 'react'
import {Route, Redirect} from 'react-router-dom';
import env from '../enviroment';

const PublicRoute = ({ component: Component, ...rest }) => {

    return (
        <Route
            {...rest}
            render={props =>
                localStorage.getItem('accessToken') == null ? (
                    <Component {...props} />
                ) : (
                    <Redirect to={{ pathname: env.APP.CHAT, state: { from: props.location } }} />
                )
            }
        />
    )
};
export default PublicRoute