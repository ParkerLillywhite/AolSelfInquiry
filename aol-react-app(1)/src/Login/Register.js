import { React, useState } from "react";
import { setJwtCookie } from "../utils/jwtUtils";
import Layout from "../Header/Layout";
import { useNavigate } from "react-router-dom";
import { logIn, logOut } from "../Actions/loggedInAction";
import { useDispatch } from "react-redux";

import "./Styles/Register.css";

const Register = () => {

    const [success, setSuccess] = useState(false);
    const [passwordError, setPasswordError] = useState(false);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const firstname = formData.get('firstname');
        const lastname = formData.get('lastname');
        const email = formData.get('email');
        const password = formData.get('password');
        const passwordRetype = formData.get('password-retype');

        try {

            if(password !== passwordRetype){
                setPasswordError(true);
                throw new Error("Passwords do not match");
            }

            const response = await fetch('http://localhost:8081/api/v1/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type' : 'application/json'
                },
                body: JSON.stringify({
                    firstname: firstname,
                    lastname: lastname,
                    email: email,
                    password: password
                })
            });

            if(response.ok) {
                console.log("successfully created user");
                setSuccess(true);
                dispatch(logIn());

                try {

                    const data = await response.json();
                    setJwtCookie(data);
    
                } catch (error) {
                    console.log("unable to set jwt cookie", error);
                }

                navigate("/");
            }

            if(!response.ok) {
                throw new Error("Error: Unable to register new user");
            }

        } catch (error) {
            console.log("Unable to register new user ", error);
        }
    }

    return(
        <Layout>
            <div className="register-container">
                <form onSubmit={handleSubmit}>
                    <div className="register-firstname">
                        <label> Firstname </label>
                        <input type="text" name="firstname"/>
                    </div>
                    <div className="register-lastname">
                        <label> Lastname </label>
                        <input type="text" name="lastname"/>
                    </div>
                    <div className="register-email">
                        <label> Email </label>
                        <input type="text" name="email"/>
                    </div>
                    {passwordError && <div className="register-password-error-message">Passwords do not match</div>}
                    <div className={passwordError ? "register-password-error" : "register-password"}>
                        <label> Password </label>
                        <input type="password" name="password" />
                    </div>
                    <div className={passwordError ? "register-password-error" : "register-retype-password"}>
                        <label>Re-type Password</label>
                        <input type="password" name="password-retype" />
                    </div>
                    <button type="submit">Submit</button>
                </form>
            </div>
        </Layout>
    )
}

export default Register;