import { React, useState } from "react";
import { useNavigate } from "react-router";
import Layout from "../Header/Layout";
import { setJwtCookie, getJwtCookie } from "../utils/jwtUtils";
import { useDispatch } from 'react-redux';
import { logIn, logOut } from "../Actions/loggedInAction";
import { updateUser } from "../Actions/UserAction";
import jwt_decode from "jwt-decode";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSpinner } from "@fortawesome/free-solid-svg-icons";

import "./Styles/Login.css"

const Login = () => {

    const navigate = useNavigate();
    const dispatch = useDispatch();

    const [ passwordError, setPasswordError ] = useState(false);
    const [ isLoading, setIsLoading ] = useState(false);

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const email = formData.get('email');
        const password = formData.get('password');

        setIsLoading(true);

        try {
            const response = await fetch('http://localhost:8081/api/v1/auth/authenticate', {
                method: 'POST',
                headers: {
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            });

            if(response.ok){
                console.log("response was successful. User is logged in")
                dispatch(logIn());
                const data = await response.json();
                setJwtCookie(data);

                const additionalUserDataResponse = await fetch(`http://localhost:8081/api/v1/user/get-data/${email}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type' : 'application/json',
                        'Authorization' : `Bearer ${getJwtCookie()}`
                    }
                });

                if(additionalUserDataResponse.ok){
                    console.log("User data was retrieved successfully");
                    const additionalUserData = await additionalUserDataResponse.json();

                    const userData = {
                        firstname: additionalUserData.firstname,
                        lastname: additionalUserData.lastname,
                        email: additionalUserData.email,
                        isAdmin: isUserAdmin(additionalUserData.role)
                    }
                    dispatch(updateUser(userData));
                } 

                if(!additionalUserDataResponse.ok){
                    throw new Error("Unable to retrieve user data");
                }

                navigate("/");

            }
    
            if(!response.ok) {
                setPasswordError(true);
                throw new Error("Unable to authenticate user");
            }

        } catch (error) {
            setPasswordError(true);
            console.log("failed to authenticate user", error);
        } finally {
            setIsLoading(false);
        }

    }

    const isUserAdmin = (data) => {
        return data === 'ADMIN';
    }

    const decodeJwtAuthority = (jwt) => {
        jwt = JSON.stringify(jwt);
        const token = jwt.replace('Bearer ', '')
        const decoded = jwt_decode(token);

        // const authorities = decoded.role[0].authority;

        return decoded;
    }

    return(
        <Layout>
            <div className="login-container">
                {isLoading ? (
                    <div className="loading-icon">
                        <FontAwesomeIcon icon={faSpinner} />
                    </div>
                ) : (
                    <>
                    <div className="login-component">
                        <form onSubmit={handleSubmit}>
                            {passwordError && <div className="login-error-message">Wrong email or password</div>}
                            <div className={passwordError ? "login-email-error" : "login-email"}>
                                <label> Email </label>
                                <input type="text" name="email"/> 
                            </div>
                            <div className={passwordError ? "login-password-error" : "login-password"}>
                                <label> Password </label>
                                <input type="password" name="password" />
                            </div>
                            <button type="submit">Submit</button>
                        </form>
                    </div>
                    <div className="new-user-container">
                        <div className="new-user"> Not Registered?</div>
                        <button className="new-user-button" onClick={() => navigate('/register')}>Register new user</button>
                    </div>
                    </>
                )}
            </div>

        </Layout>
    )
}

export default Login;