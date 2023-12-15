import { UPDATE_USER } from "../Actions/ActionTypes/UserActionTypes";

const initialState = {
    user: {
        firstname: '',
        lastname: '',
        email: '',
        isAdmin: 'false'
    }
}

const userReducer = (state = initialState, action) => {
    switch (action.type) {
        case UPDATE_USER:
            return {
                ...state,
                user: {
                    ...state.user,
                    ...action.payload,
                },
            };

        default:
            return state;
    }
}

export default userReducer;