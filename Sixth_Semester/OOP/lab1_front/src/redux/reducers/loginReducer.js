import AuthenticationService from "../services/AuthenticationAPI";
import jwt from "jwt-decode";

const LOGIN_USER_STARTED="LOGIN_USER_STARTED";
export const LOGIN_USER_SUCCESS="LOGIN_USER_SUCCESS";
export const LOGIN_USER_FAILED="LOGIN_USER_FAILED";
export const LOGOUT_USER="LOGOUT_USER";

const initialState={
    isLoginFailed:false,
    isLoading:false,
    isLogged:false,
    roles: null,
    token: null
}

export default function loginReducer(state=initialState, action){
    switch (action.type) {
        case LOGIN_USER_STARTED:
            return{
                ...state,
                isLoading: true,
                isLoginFailed: false,
                isLogged:false,
                roles: null
            }
        case LOGIN_USER_SUCCESS:
            return{
                ...state,
                isLoading: false,
                isLoginFailed: false,
                isLogged:true,
                roles: action.payload.roles,
                token: action.payload.token
            }
        case LOGIN_USER_FAILED:
            return{
                ...state,
                isLoading: false,
                isLoginFailed: true,
                isLogged:false,
                roles: null,
                token: null
            }
        case LOGOUT_USER:
            return initialState;
        default:
            return state;

    }
}

function loginUserStarted(){
    return {type:LOGIN_USER_STARTED};
}

function loginUserSuccess(roles, token){
    return {type:LOGIN_USER_SUCCESS,payload:{roles, token}};
}

function loginUserFailed(){
    return {type:LOGIN_USER_FAILED};
}

export function executeLogout(){
    return {type:LOGOUT_USER};
}

export function executeLogin(username,password){
    return (dispatch)=> {
        dispatch(loginUserStarted());
        AuthenticationService.executeAuthentication(username, password)
            .then((response) => {
                let token=response.data.token;
                let info = jwt(token);
                let roles = info.authorities.split(';');
                dispatch(loginUserSuccess(roles, token));
            })
            .catch(() => {
                dispatch(loginUserFailed());
                console.log("Not Authenticated");
            })
    }
}