import {URL} from "../../utils/UrlConstraints";
import axios from "axios";

class UsersAPI {
    getAllUsers() {
        return axios.get(`${URL}/users`);
    }

    create(userInfo) {
        return axios.post(`${URL}/user`,
            userInfo
        )
    }
}

export default new UsersAPI();