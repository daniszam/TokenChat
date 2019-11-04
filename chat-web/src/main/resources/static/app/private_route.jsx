import React from 'react'
import {Redirect, Route} from 'react-router-dom';
import env from '../enviroment';

const PrivateRoute = ({component: Component, ...rest}) => {

    return (
        <Route
            {...rest}
            render={props =>
                localStorage.getItem('accessToken') != null ? (
                    <Component {...props} />
                ) : (
                    <Redirect to={{pathname: env.APP.LOGIN, state: {from: props.location}}}/>
                )
            }
        />
    )
};
export default PrivateRoute