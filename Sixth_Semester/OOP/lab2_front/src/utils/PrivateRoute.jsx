import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import AuthenticationService from "../redux/services/AuthenticationAPI";
import connect from "react-redux/lib/connect/connect";

const PrivateRoute = ({ component: Component, requiredRoles,nonAuthorised, userRoles, isLogged, ...rest }) => (
    <Route {...rest} render={props => {
        //not logged in, but nonAuthorised flag
        if(nonAuthorised && !isLogged){
            return <Component {...props} />
        }
        // not logged in so redirect to login page with the return url
        if (!isLogged) {
            //return null;
            return <Redirect to={{ pathname: '/login'}} />
            //props.history.push("/login");
            //return null;
        }

        // check if route is restricted by role
        let isRolePass=false;
        let accessRoles=requiredRoles;
        for(let i=0;i<userRoles.length;i++){
            if(accessRoles && accessRoles.indexOf(userRoles[i])!==-1){
                isRolePass=true;
                break;
            }
        }

        // role not authorised so redirect to home page
        if (!isRolePass) {
            return <Redirect to={{ pathname: '/'}} />
            //return null;
        }

        // authorised so return component
        return <Component {...props} />
    }} />
)

export default PrivateRoute;