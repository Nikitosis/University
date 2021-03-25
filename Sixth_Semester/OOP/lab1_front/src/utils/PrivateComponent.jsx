import React from 'react';
import {Redirect} from "react-router-dom";
import {connect} from "react-redux";
import CardsPage from "../components/content/CardsPage";

function PrivateComponent(props){
        if(props.nonAuthorised && !props.isLogged){
            return props.children;
        }

        if(props.nonAuthorised && props.isLogged) {
            return null;
        }

        if (!props.isLogged) {
            // not logged in so redirect to authentication page with the return url
            return null;
            //return <Redirect to={{ pathname: '/login', state: { from: props.location } }} />
        }

        // check if route is restricted by role
        let isRolePass=false;
        let accessRoles=props.requiredRoles!==undefined? props.requiredRoles : null;
        let userRoles=props.roles;
        for(let i=0;i<userRoles.length;i++){
            if(accessRoles && accessRoles.indexOf(userRoles[i])!==-1){
                isRolePass=true;
                break;
            }
        }

        if (!isRolePass) {
            // role not authorised so redirect to home page
            return null;
        }

        // authorised so return component
        return props.children;
}
function mapStateToProps(state){
    return{
        roles:state.loginReducer.roles,
        isLogged:state.loginReducer.isLogged
    }
}

export default connect(mapStateToProps,null)(PrivateComponent);