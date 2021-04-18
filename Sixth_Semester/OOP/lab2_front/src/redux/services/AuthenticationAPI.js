import axios from 'axios';
import {URL} from "../../utils/UrlConstraints";

const API_URL=URL;

export const USER_NAME_SESSION_ATTRIBUTE="authenticatedUsername";
export const USER_ID_SESSION_ATTRIBUTE="authenticatedUserId";
export const USER_ROLES_SESSION_ATTRIBUTE="authenticatedUserRoles";

const LOCAL_STORAGE_TOKEN="token";

class AuthenticationAPI{
    executeAuthentication(username,password){
        return axios.post(`${URL}/login`,{
                "username":username,
                "password":password
        })
    }

    executeRefreshToken(refreshToken) {
        return axios.post(`${URL}/refresh-token`,{
            "refreshToken":refreshToken,
        })
    }
}

export default new AuthenticationAPI();