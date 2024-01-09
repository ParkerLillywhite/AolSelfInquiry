import React, { useState, useEffect } from "react";
import Calendar from "react-calendar";
import "react-calendar/dist/Calendar.css";
import { getJwtCookie } from "../utils/jwtUtils";
import "./Styles/AolCalendarStyles.css";
import { useSelector } from "react-redux";

const AolCalendar = () => {
    //change

    const [date, setDate] = useState(new Date());
    const [disabledDates, setDisabledDates] = useState([]);
    const [isLoading, setIsLoading] = useState(false);

    const today = new Date();
    const yesterday = new Date(today);
    const tommorrow = new Date(today);
    const futureDateCap = new Date(today);

    yesterday.setDate(today.getDate() - 1);
    tommorrow.setDate(today.getDate() + 1);
    futureDateCap.setDate(today.getDate() + 364);

    const isAdminSelector = (state) => state.user.user.isAdmin;

    const isAdmin = useSelector(isAdminSelector);

    function changeValue(val) {
        setDate(val);
    }

    const getDisabledDates = async () => {
        try {
            const response = await fetch('http://localhost:8081/api/v1/date/disabled-dates', {
                method: 'GET',
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization' : `Bearer ${getJwtCookie()}`
                }
            });
            
            if(response.ok) {
                console.log('response was successful');
                const data = await response.json();

                setDisabledDates([...disabledDates, ...data]);
            }
        } catch (error) { 
            console.log('unable to fetch disabled dates', error);
        }
    }

    useEffect(() => {
        getDisabledDates();
    }, []);

    const titleDisabled = ({ date }) => {
        return disabledDates.some(disabledDate => 
            new Date(disabledDate.date).toDateString() === date.toDateString()
        );
    }

    return (
        <div className="calendar-container">
            
            <Calendar 
                onChange = {changeValue}
                value = {date}
                minDate = {tommorrow}
                maxDate = {futureDateCap}
                showWeekNumbers = {true}
                showNeighboringMonth = {true}
                calendarType = "US"
                titleDisabled = {titleDisabled}

            />
            <p> the selected date is - {date.toLocaleDateString()} </p>
        </div>
    )
}

export default AolCalendar;