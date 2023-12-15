import { LOG_IN, LOG_OUT } from "../Actions/ActionTypes/loggedInActionTypes";

const initialState = {
    loggedIn: false,
};

const loggedInReducer = (state = initialState, action) => {
    switch(action.type) {
        case LOG_IN:
            return {
                ...state,
                loggedIn: state.loggedIn = true,
            };

        case LOG_OUT:
            return { 
                ...state,
                loggedIn: state.loggedIn = false,
            };
        
            default:
                return state;
    }
}

export default loggedInReducer;