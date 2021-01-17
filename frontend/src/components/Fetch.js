import facade from "../apiFacade";
import React, { useState,useEffect } from "react"
import {useParams} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

//The booking functionality isn't quite working here 

export default function FetchData(){
  const [message, setMessage] = useState(null);
  const [tableRows, setTableRows] = useState("");
  const {strategy} = useParams();
  const [hotelID, setHotelID] = useState("");
  const [booking, setBooking] = useState({startDate:"", numberOfNights: "", hotelID:""  });


  const onChange = (evt) => {
    setBooking({ ...booking, [evt.target.id]: evt.target.value});//Uses the spread operator, takes the properties already in user(Right now empty strings) (or changes them if the name of the property is the same) + the values of the event's target id (they need to match the value eg. username and password.)
    //In Danish just cause:
    //den tager alle properties fra user, og tilføjer en ny , eller erstatter en hvis navn af property er den samme
    //så evt.target.id er et navn af property, som i vores tilfælde username eller password og evn.target.value er value af den property

  };
  function getAllHotels(){
      facade.fetchData(strategy+'/all')
      .then(data => {
          setTableRows(data.map((hotel) =>
          (<tr key={hotel.id}>
          <td>{hotel.id}</td>
          <td>{hotel.name}</td>
          <td>{hotel.address}</td>
          <td>{hotel.title}</td>
          <td>{hotel.phone}</td>
          <td>{hotel.content}</td>
          <td>{hotel.url}</td>
          <td><button id={hotel.id} onClick={addBooking} >Book Hotel!</button></td>    
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
      getAllHotels()
      
  },[strategy]) //What useEffect is listening for changes on


const displayHotels = setTableRows? (
  <table className="table" style={{width: "90%"}}>
  <thead>
    <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Address</th>    
          <th>City</th>    
          <th>Phone Number</th>    
          <th>Description</th>    
          <th>URL</th>    
    </tr>
  </thead>
  <tbody id="tbody">{tableRows}</tbody>
</table>
) : "Loading..."

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
    <h2>Hotel List</h2>
    {!message && displayHotels}
    <p>{message}</p>

  </div>
  
);

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

}