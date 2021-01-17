import React, { useState,useEffect } from "react"
import facade from "../apiFacade";

export default function CustomerPage() {
    const [render, setRender] = useState(false);
    const [message, setMessage] = useState(null);
    const [tableRowsBookings, setTableRowsBookings] = useState("");
 
 
    useEffect(() =>{ //Takes care of the stuff that ComponentDidMount used to
        setMessage("")
        getAllBookingsByCustomer()
        // setDataFromServer(null)
        
    },[render]) //What useEffect is listening for changes on



    function getAllBookingsByCustomer(){
        facade.getAllBookingsByCustomer()
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
      function deleteBooking(evt){
        evt.preventDefault();
        facade.deleteBooking(evt.target.id)
        setRender(!render)
        setMessage("Booking deleted");
        getAllBookingsByCustomer()
    }
    
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
        <h2>NOT WORKING RIGHT NOW</h2>
       <h2>All Bookings</h2>
       {!message && displayBookings}
       <p>{message}</p>
    </div>
    
  );
  
 }
// export default function ShowBookings() {
//     const [user, setUser] = useState("");
//     const [tableRowsBookings, setTableRowsBookings] = useState("");
//     const [message, setMessage] = useState("");
   
//     function getAllBookingsByCustomer(){
//         console.log(user);
//                 facade.getAllBookingsByCustomer(user.username)
//                 .then(data => {
//                   console.log(data);
//                   setTableRowsBookings(data.map((booking) =>
//                     (<tr key={booking.ID}>
//                     <td>{booking.ID}</td> 
//                     <td>{booking.startDate}</td> 
//                     <td>{booking.numberOfNights}</td> 
//                     <td>{booking.customerUsername}</td>   
//                     <td>{booking.hotelID}</td>   
//                     <td><button id={booking.ID} onClick={deleteBooking} >Delete</button></td>    
//                     </tr>)
//                 )                 
//                       )})
//                 .catch((err) => {
//                     if (err.status) {
//                       err.fullError.then((e) => {
//                         console.log(e.message);
//                         setMessage(e.message);
//                       });
//                     } else {
//                       console.log("Error occurred!");
//                       setMessage("Error occurred!");
//                     }
          
//                   });
//               }
  
//     const onChange = (evt) => {
//       setUser({[evt.target.id]: evt.target.value });//Uses the spread operator, takes the properties already in user(Right now empty strings) (or changes them if the name of the property is the same) + the values of the event's target id (they need to match the value eg. username and password.)
//       //In Danish just cause:
//       //den tager alle properties fra user, og tilføjer en ny , eller erstatter en hvis navn af property er den samme
//       //så evt.target.id er et navn af property, som i vores tilfælde username eller password og evn.target.value er value af den property
  
  
  
//     };
//     function deleteBooking(evt){
//         evt.preventDefault();
//         facade.deleteBooking(evt.target.id)
//         // setRender(!render)
//         setMessage("Booking deleted");
//         getAllBookingsByCustomer()
//     }
//     function displayBookings(){
//         return(<table className="table" style={{width: "90%"}}>
//         <thead>
//           <tr>
//             <th>ID</th>
//             <th>Start Date</th>
//             <th>Number Of Nights</th>
//             <th>Customer Username</th>
//             <th>Hotel ID</th>
//           </tr>
//         </thead>
//         <tbody id="tbody">{tableRowsBookings}</tbody>
//       </table>)
//     }
//     return (
//       <div>
//           {displayBookings}
//           <h2>NOT WORKING RIGHT NOW</h2>
//         {/* <h2>Bookings</h2> */}
        
        
//         <label for="username">Username</label>
//         <br></br>
//         <input
//           type="text"
//           placeholder="username"
//           className="textInputField"
//           id="username"
//           onChange={onChange}
//         />
//         <br></br>
  
        
//         <br></br>
//         <br></br>
//         <button onClick={getAllBookingsByCustomer}>Register!</button>
        
//         <p>{message}</p>
//       </div>
//     );
//   }