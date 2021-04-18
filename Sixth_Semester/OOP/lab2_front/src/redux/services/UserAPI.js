import axios from "axios";
import {URL} from "../../utils/UrlConstraints";

const API_URL=URL;

class UserAPI{

    getAll(){
        return axios.get(`${API_URL}/users`)
    }

    getUserInfo(userId){
        return axios.get(`${API_URL}/users/${userId}`)
    }

    getMyInfo(){
        return axios.get(`${API_URL}/user`)
    }

    createUser(user){
        let userInfoJson=JSON.stringify(user);
        let userInfo=new Blob([userInfoJson],{
            type:'application/json'
        });

        let formData=new FormData();
        formData.append('userInfo',userInfo);
        return axios.post(`${API_URL}/users`,
            formData,
            {
                headers:{
                    'content-type': 'multipart/form-data'
                },
        })
    }

    updateUser(user,avatar){
        let userInfoJson=JSON.stringify(user);
        let userInfo=new Blob([userInfoJson],{
            type:'application/json'
        });

        let formData=new FormData();
        formData.append('userInfo',userInfo);
        if(avatar!=null)
            formData.append("image",avatar);
        return axios.put(`${API_URL}/users`,
            formData,
            {
                headers:{
                    'content-type': 'multipart/form-data'
                },
            })
    }

    depositMoney(userId,money){
        return axios.put(`${API_URL}/users/${userId}/money`,
            {
                money:money
        })
    }
}

export default new UserAPI();