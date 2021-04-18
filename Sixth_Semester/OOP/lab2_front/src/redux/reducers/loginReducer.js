import AuthenticationService from "../services/AuthenticationAPI";
import jwt from "jwt-decode";

const LOGIN_USER_STARTED="LOGIN_USER_STARTED";
export const LOGIN_USER_SUCCESS="LOGIN_USER_SUCCESS";
export const LOGIN_USER_FAILED="LOGIN_USER_FAILED";
export const LOGOUT_USER="LOGOUT_USER";

export const REFRESH_TOKEN_STARTED = "REFRESH_TOKEN_STARTED";

const initialState={
    isLoginFailed:false,
    isLoading:false,
    isLogged:false,
    roles: null,
    token: null,
    refreshToken: null
}

export default function loginReducer(state=initialState, action){
    switch (action.type) {
        case REFRESH_TOKEN_STARTED:
            return {
                ...state,
                token: null
            }
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
                token: action.payload.token,
                refreshToken: action.payload.refreshToken
            }
        case LOGIN_USER_FAILED:
            return{
                ...state,
                isLoading: false,
                isLoginFailed: true,
                isLogged:false,
                roles: null,
                token: null,
                refreshToken: null
            }
        case LOGOUT_USER:
            return initialState;
        default:
            return state;

    }
}
function refreshTokenStarted() {
    return {type:REFRESH_TOKEN_STARTED}
}

function loginUserStarted(){
    return {type:LOGIN_USER_STARTED};
}

function loginUserSuccess(roles, token, refreshToken){
    return {type:LOGIN_USER_SUCCESS,payload:{roles, token, refreshToken}};
}

function loginUserFailed(){
    return {type:LOGIN_USER_FAILED};
}

export function executeLogout(){
    return {type:LOGOUT_USER};
}

export function executeRefreshToken(refreshToken) {
    return (dispatch)=> {
        debugger;
        dispatch(refreshTokenStarted());
        AuthenticationService.executeRefreshToken(refreshToken)
            .then((response) => {
                debugger;
                let token=response.data.token;
                let refreshToken = response.data.refreshToken;
                let info = jwt(token);
                let roles = info.realm_access.roles;
                dispatch(loginUserSuccess(roles, token, refreshToken));
            })
            .catch(() => {
                debugger;
                dispatch(loginUserFailed());
                console.log("Unable to refresh token");
            })
    }
}

export function executeLogin(username,password){
    return (dispatch)=> {
        dispatch(loginUserStarted());
        AuthenticationService.executeAuthentication(username, password)
            .then((response) => {
                let token=response.data.token;
                let refreshToken = response.data.refreshToken;
                let info = jwt(token);
                let roles = info.realm_access.roles;
                dispatch(loginUserSuccess(roles, token, refreshToken));
            })
            .catch(() => {
                dispatch(loginUserFailed());
                console.log("Not Authenticated");
            })
    }
}