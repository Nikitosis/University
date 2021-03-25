import axios from "axios";
import {executeLogout} from "./reducers/loginReducer";

export function setupInterceptors(store){
    axios.interceptors.request.use(
        (config)=>{
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
            if (403 === error.response.status) {
                console.log("unauthorised, loggint out...");
                store.dispatch(executeLogout());
            }
            return Promise.reject(error);
        }
    )
}
