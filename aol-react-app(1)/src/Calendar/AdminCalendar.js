import React, { useEffect, useState } from "react";
import { getJwtCookie } from "../utils/jwtUtils";
import './Styles/AdminCalendar.css';
import { Calendar } from "react-multi-date-picker";
import DatePicker from "react-multi-date-picker";
const AdminCalendar = () => {

    const [date, setDate] = useState(new Date());
    const [test, setTest] = useState([]);
    const [disabledDates, setDisabledDates] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [selectedDisableDates, setSelectedDisableDates] = useState([]);

    const today = new Date();
    const tomorrow = new Date(today);
    const yesterday = new Date(today);
    const futureDateCap = new Date(today);

    yesterday.setDate(today.getDate() - 1);
    tomorrow.setDate(today.getDate() + 1);
    futureDateCap.setDate(today.getDate() + 364);

    function changeValue(val) {
        setDate(val);
    }

    const handleSubmit = async () => {

        setIsLoading(true);

        try { 

            const dateObjects = selectedDisableDates.map(date => {
                const dateObject = new Date(date);

                return dateObject;
            })

            const stringDates = dateObjects.map(dateString => dateString.toISOString());

            console.log(stringDates);

            const response = await fetch('http://localhost:8081/api/v1/date/create-disabled', {
                method: 'POST',
                headers: {
                    'Content-type' : 'application/json',
                    'Authorization' : `Bearer ${getJwtCookie()}`
                },
                body: JSON.stringify(stringDates)
            });

            if(response.ok) {
                console.log('response was successful - created disabled date');
                const data = await response.json();

                console.log("cancelledDate response: ", data);

                setSelectedDisableDates([]);

                //const timeCancelledResponses = data.map((item, index) => {

                //});
            }

        } catch (error) {
            console.log('failed to create disabled date', error);
        } finally {
            setIsLoading(false);
        }

        setSelectedDisableDates([]);
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
                console.log('disabled dates response was successful');
                const data = await response.json();

                setDisabledDates([...disabledDates, ...data])
            }

        } catch (error) {
            console.log('failed to retreive disabled dates', error);
        }
    }
    
    const titleDisabled = ({ date }) => {
        return disabledDates.some(disabledDate => 
                new Date(disabledDate.date).toDateString() === date.toDateString()
            );
    }
    const handleDateChange = (date) => {
        setSelectedDisableDates(date);
    }

    useEffect(() => {
        getDisabledDates();
    }, [])

    return (
        <div className="admin-calendar-container">
            <div className="date-disabler-container">
                <DatePicker 
                    multiple
                    value = {selectedDisableDates}
                    onChange = {handleDateChange}
                    minDate = {tomorrow}
                    maxDate = {futureDateCap}
                    excludeDates = {getDisabledDates}
                />
                <div className="disable-date-selector">
                    <p> Dates to be disabled: {selectedDisableDates.join(", ")}</p>
                    <button onClick={() => handleSubmit()}>Submit</button>
                </div>
            </div>
        


        </div>
    )
}

export default AdminCalendar;