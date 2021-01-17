import React, { useState,useEffect } from "react"
import facade from "../apiFacade";

export default function Admin() {
    const [render, setRender] = useState(false);
    const [message, setMessage] = useState(null);
    const [tableRowsUsers, setTableRowsUsers] = useState("");
    const [tableRowsBookings, setTableRowsBookings] = useState("");

    // function editUser(evt){
    //     evt.preventDefault();
    //     facade.editUser(evt.target.id)
    //     userToEdit = {username: evt.target.id, password}
    // }
    function deleteUser(evt){
        evt.preventDefault();
        facade.deleteUser(evt.target.id)
        setRender(!render)
        setMessage("User deleted");
        getAllUsers()
    }
    function deleteBooking(evt){
        evt.preventDefault();
        facade.deleteBooking(evt.target.id)
        setRender(!render)
        setMessage("Booking deleted");
        getAllBookings()
    }
    function getAllUsers(){
        facade.getAllUsers()
        .then(data => {
          console.log(data);
          setTableRowsUsers(data.map((user) =>
            (<tr key={user.username}>
            <td>{user.username}</td> 
            <td>{user.name}</td>   
            <td>{user.phone}</td>   
            <td><button id={user.username} onClick={deleteUser} >Delete</button></td>    
            </tr>)
        )                 
              )})
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
    }
    function getAllBookings(){
      facade.getAllBookings()
      .then(data => {
        console.log(data);
        setTableRowsBookings(data.map((booking) =>
          (<tr key={booking.ID}>
          <td>{booking.ID}</td> 
          <td>{booking.startDate}</td> 
          <td>{booking.numberOfNights}</td> 
          <td>{booking.customerUsername}</td>   
          <td>{booking.hotelID}</td>   
          <td><button id={booking.ID} onClick={deleteBooking} >Delete</button></td>    
          </tr>)
      )                 
            )})
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
    }
    useEffect(() =>{ //Takes care of the stuff that ComponentDidMount used to
        setMessage("")
        getAllUsers()
        getAllBookings()
        // setDataFromServer(null)
        
    },[render]) //What useEffect is listening for changes on


 const displayUsers = tableRowsUsers  ? (
    <table className="table" style={{width: "90%"}}>
    <thead>
      <tr>
        <th>Name</th>
        <th>Username</th>
        <th>Phone</th>
      </tr>
    </thead>
    <tbody id="tbody">{tableRowsUsers}</tbody>
  </table>
  
 ) : "Loading..."

 
 const displayBookings = tableRowsBookings ? (
    <table className="table" style={{width: "90%"}}>
    <thead>
      <tr>
        <th>ID</th>
        <th>Start Date</th>
        <th>Number Of Nights</th>
        <th>Customer Username</th>
        <th>Hotel ID</th>
      </tr>
    </thead>
    <tbody id="tbody">{tableRowsBookings}</tbody>
  </table>
  
 ) : "Loading..."

 return (    
    <div>
      <h2>All Users</h2>
      {!message && displayUsers}
      <p>{message}</p>
      <h2>All Bookings</h2>
      {!message && displayBookings}
      <p>{message}</p>
    </div>
    
  );
  
 }