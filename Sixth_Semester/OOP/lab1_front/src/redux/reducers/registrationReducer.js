import UsersAPI from "../services/UsersAPI";
import {fetchUsersFailure, fetchUsersStarted, fetchUsersSuccess} from "./usersReducer";

const REGISTRATION_MODAL_OPEN="REGISTRATION_MODAL_OPEN";
const REGISTRATION_MODAL_CLOSE="REGISTRATION_MODAL_CLOSE";

const CREATE_USER_STARTED="CREATE_USER_STARTED";
const CREATE_USER_SUCCESS="CREATE_USER_SUCCESS";
const CREATE_USER_FAILURE="CREATE_USER_FAILURE";

const initialState={
    isRegistrationModalOpened: false
};

export default function registrationReducer(state=initialState, action) {
    switch(action.type) {
        case REGISTRATION_MODAL_OPEN:
            debugger;
            return {
                ...state,
                isRegistrationModalOpened: true
            }
        case REGISTRATION_MODAL_CLOSE:
            return {
                ...state,
                isRegistrationModalOpened: false
            }
        default:
            return initialState;
    }
}

export function openRegistrationModal() {
    return {type: REGISTRATION_MODAL_OPEN};
}

export function closeRegistrationModal() {
    return {type: REGISTRATION_MODAL_CLOSE};
}

export function createUserStarted() {
    return {type: CREATE_USER_STARTED};
}

export function createUserSuccess() {
    return {type: CREATE_USER_SUCCESS};
}

export function createUserFailure() {
    return {type: CREATE_USER_FAILURE};
}


export function createUser(userInfo) {
    return (dispatch,getState)=>{
        debugger;
        dispatch(createUserStarted());
        UsersAPI.create(userInfo)
            .then(res=> {
                    debugger;
                    dispatch(createUserSuccess());
                }
            )
            .catch((e)=>{
                debugger;
                dispatch(createUserFailure());
            })
    };
}