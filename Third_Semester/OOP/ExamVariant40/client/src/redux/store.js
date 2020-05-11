import {applyMiddleware, combineReducers, createStore} from "redux";
import thunk from "redux-thunk";
import competitionsReducer from "./reducers/competitionsReducer";
import competitionInfoReducer from "./reducers/competitionInfoReducer";

const reducers=combineReducers({
    competitionsReducer,
    competitionInfoReducer
});
const store=createStore(
    reducers,
    applyMiddleware(thunk)
);

export default store;
