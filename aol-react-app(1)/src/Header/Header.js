import React from "react";
import { useNavigate, useLocation } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { logOut } from "../Actions/loggedInAction";
import { updateUser } from "../Actions/UserAction";

const Header = () => {

    const loggedInSelector = (state) => state.loggedInStatus.loggedIn;
    const userDataSelector = (state) => state.user.isAdmin;

    const navigate = useNavigate();
    const location = useLocation();
    const loggedIn = useSelector(loggedInSelector);
    const isAdmin = useSelector(userDataSelector);

    const dispatch = useDispatch();

    const userLogOutData = {
        firstname: "",
        lastname: "",
        email: "",
        isAdmin: false
    }

    const handleLogOut = () => {
        dispatch(logOut());
        dispatch(updateUser(userLogOutData));
    }

    return(
        <NavBar>
            {location.pathname !== '/' && (
                <div className="home-navigation" onClick={() => navigate('/')}>
                    Home
                </div>
            )}
            {location.pathname !== 'admin-calendar' && isAdmin && (
                <div className="admin-navigation" onClick={() => navigate('/admin-calendar')}>
                    Admin
                </div>
            )}
            {!location.pathname.includes('login') && !loggedIn && (
                <div className="login-navigation" onClick={() => navigate('/login')}>
                    Login
                </div>
            )}
            {loggedIn && (
                <button className="logout-navigation" onClick={() => handleLogOut()}>
                    Log Out
                </button>
            )}
        </NavBar>
    )
}

const NavBar = (props) => {
    return(
        <div className="navbar-container">
            <ul className="navbar"> {props.children} </ul>
        </div>
    )
}

export default Header;