import { LOG_IN, LOG_OUT } from "./ActionTypes/loggedInActionTypes";

const logIn = () => {
    return {
        type: LOG_IN,
    }
}

const logOut = () => {
    return {
        type: LOG_OUT,
    }
}

export { logIn, logOut };