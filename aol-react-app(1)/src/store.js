import { createStore, combineReducers } from "redux";
import loggedInReducer from "./Reducers/loggedInReducer";
import userReducer from "./Reducers/userReducer";

const rootReducer = combineReducers({
    user: userReducer,
    loggedInStatus: loggedInReducer
});

const store = createStore(rootReducer);

export default store;