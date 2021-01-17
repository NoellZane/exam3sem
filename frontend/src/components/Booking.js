import facade from "../apiFacade";
import React, { useState, useEffect } from "react";

export default function Booking() {
  const [booking, setBooking] = useState({startDate:"", numberOfNights: "", hotelID:"" });
  const [message, setMessage] = useState("");

  function addBooking() {
    console.log(booking);
    if (booking) {
      facade.addBooking(booking)
        .then((data) => {
          console.log("Booking created.");
          setMessage("Booking created!");
        })
        .catch((err) => {
          if (err.status) {
            err.fullError.then((e) => {
              console.log(e.message);
              setMessage(e.message);
            });
          } else {
            console.log("Error occurred!");
            setMessage("Error occurred!");
          }
        });
    } else {
        setMessage("Check start date please");
    }
  }

  const onChange = (evt) => {
    setBooking({ ...booking, [evt.target.id]: evt.target.value });//Uses the spread operator, takes the properties already in user(Right now empty strings) (or changes them if the name of the property is the same) + the values of the event's target id (they need to match the value eg. username and password.)
    //In Danish just cause:
    //den tager alle properties fra user, og tilføjer en ny , eller erstatter en hvis navn af property er den samme
    //så evt.target.id er et navn af property, som i vores tilfælde username eller password og evn.target.value er value af den property



  };

  return (
    <div>
      <h2>Make a booking!</h2>

      <label for="startDate">Start Date</label>
      <br></br>
      <input
        type="text"
        className="textInputField"
        id="startDate"
        onChange={onChange}
      />
      <br></br>

      <label for="numberOfNights">Choose number of nights:</label>
      <br></br>
      <input
        type="number"
        className="textInputField"
        id="numberOfNights"
        onChange={onChange}
      />
      <br></br>
{/* 
      <label for="customerUsername">Username</label>
      <br></br>
      <input
        type="text"
        className="textInputField"
        id="customerUsername"
        onChange={onChange}
      />
      <br></br> */}


      <label for="hotelID">Hotel ID</label>
      <br></br>
      <input
        type="number"
        className="textInputField"
        id="hotelID"
        onChange={onChange}
      />
      <br></br>
      <br></br>
      <button onClick={addBooking}>Register!</button>
      <p>{message}</p>
    </div>
  );
}
