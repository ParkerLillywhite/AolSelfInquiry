import React from "react";
import { useSelector } from "react-redux";
import AolCalendar from "../Calendar/AolCalendar";
import AdminCalendar from "../Calendar/AdminCalendar";
import Layout from '../Header/Layout';

const Schedule = () => {

    const firstnameSelector = (state) => state.user.user.firstname;
    const lastnameSelector = (state) => state.user.user.lastname;
    const emailSelector = (state) => state.user.user.email;
    const isAdminSelector = (state) => state.user.user.isAdmin;

    const firstname = useSelector(firstnameSelector);
    const lastname = useSelector(lastnameSelector);
    const email = useSelector(emailSelector);
    const isAdmin = useSelector(isAdminSelector);

    return (
        <Layout>
            <div className="schedule-container">
                <AolCalendar />
                
                <AdminCalendar />
                
            </div>
        </Layout>
    )
}


export default Schedule