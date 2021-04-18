import axios from "axios";
import {executeLogout, executeRefreshToken} from "./reducers/loginReducer";

export function setupInterceptors(store){
    axios.interceptors.request.use(
        (config)=>{
            if(store.getState().loginReducer.token === null) {
                return config;
            }
            console.log("Authorized token: ");
            console.log(store.getState().loginReducer.token);
            config.headers.Authorization="Bearer " + store.getState().loginReducer.token;
            return config;
        }
    );

    axios.interceptors.response.use(
        (response)=> {
            return response;
        },
        (error)=>{
            if (403 === error.response.status || 401 === error.response.status) {
                console.log("unauthorised");
                if(store.getState().loginReducer.refreshToken !== null) {
                    debugger;
                    console.log("Refreshing token");
                    store.dispatch(executeRefreshToken(store.getState().loginReducer.refreshToken));
                }
            }
            return Promise.reject(error);
        }
    )
}
