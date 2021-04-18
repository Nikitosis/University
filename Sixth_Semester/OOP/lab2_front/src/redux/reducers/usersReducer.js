import CardsAPI from "../services/CardsAPI";
import UsersAPI from "../services/UsersAPI";

const FETCH_USERS_STARTED="FETCH_USERS_STARTED";
const FETCH_USERS_SUCCESS="FETCH_USERS_SUCCESS";
const FETCH_USERS_FAILURE="FETCH_USERS_FAILURE";

const initialState={
    users:[]
}

export default function usersReducer(state=initialState, action) {
    switch (action.type) {
        case FETCH_USERS_SUCCESS:
            debugger;
            return {
                ...state,
                users: action.payload
            }
        default:
            return initialState;
    }
}

export function fetchUsersStarted() {
    return {type: FETCH_USERS_STARTED};
}

export function fetchUsersSuccess(users) {
    return {type: FETCH_USERS_SUCCESS, payload:users};
}

export function fetchUsersFailure() {
    return {type: FETCH_USERS_FAILURE};
}

export function fetchUsers() {
    return (dispatch,getState)=>{
        dispatch(fetchUsersStarted());
        UsersAPI.getAllUsers()
            .then(res=> {
                    const users = res.data;
                    dispatch(fetchUsersSuccess(users));
                }
            )
            .catch((e)=>{
                debugger;
                dispatch(fetchUsersFailure());
            })
    };
}