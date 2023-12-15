import React from "react";
import { PopupButton, useCalendlyEventListener } from "react-calendly";

const Calendly = (props) => {
    
    useCalendlyEventListener({
        onEventScheduled: (e) => console.log(e.data.payload)
    });
    
    return (
        <div className="calendly-container">
            <PopupButton 
                url={`http://calendly.com/parkerlillywhite/${props.meetingType}`}
                rootElement={document.getElementById("root")}
                text={`Click here to schedule ${props.meetingType} appointment`}
                prefill={props.prefill}
            />

        </div>
    )
}
export default Calendly;