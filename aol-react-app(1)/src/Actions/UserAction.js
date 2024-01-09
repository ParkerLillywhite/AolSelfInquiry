import { UPDATE_USER } from "./ActionTypes/UserActionTypes";

export const updateUser = (userUpdates) => {
    return {
        type: UPDATE_USER,
        payload: userUpdates
    }
}