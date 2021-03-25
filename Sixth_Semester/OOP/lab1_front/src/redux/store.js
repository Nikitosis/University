import throttle from "lodash/throttle";
import {applyMiddleware, combineReducers, createStore} from "redux";
import loginReducer from "./reducers/loginReducer";
import {loadState, saveState} from "./localStoreManager";
import thunk from "redux-thunk";
import * as AxiosInterceptors from "./AxiosInterceptors";
import cardsReducer from "./reducers/cardsReducer";
import usersReducer from "./reducers/usersReducer";
import registrationReducer from "./reducers/registrationReducer";

const reducers=combineReducers({
    registrationReducer,
    usersReducer,
    cardsReducer,
    loginReducer
});
const persistedState=loadState();
const store=createStore(
    reducers,
    persistedState,
    applyMiddleware(thunk)
);

AxiosInterceptors.setupInterceptors(store);

//save state to localstorage every second
store.subscribe(throttle(()=>{
        saveState(store.getState());
    },
    1000));

export default store;