import React from "react";
import Layout from '../Header/Layout';
import { useDispatch, useSelector } from 'react-redux';
import { logIn, logOut } from "../Actions/loggedInAction";
import Schedule from "../Scheduling/Schedule";
import { useNavigate } from "react-router";

const Home = () => {
    const loggedInSelector = (state) => state.loggedInStatus.loggedIn;
    const userDataSelector = (state) => state.user.user.firstname;

    const dispatch = useDispatch();
    const loggedIn = useSelector(loggedInSelector);
    const firstname = useSelector(userDataSelector);

    const navigate = useNavigate();

    return (
        <Layout>
            <div className="home-container">
                Home...
                {loggedIn && 
                    <div className="greeting-message">
                        Hello {firstname}!
                        <button className="schedule-appointment" onClick={() => navigate('/schedule')}>
                            Schedule an appointment
                        </button>
                    </div>
                }
                
            </div>
        </Layout>
    )
}

export default Home;
