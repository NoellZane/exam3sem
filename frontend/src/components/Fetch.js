import facade from "../apiFacade";
import React, { useState,useEffect } from "react"
import {useParams} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';



export default function FetchData(){
  const [message, setMessage] = useState(null);
  const [tableRows, setTableRows] = useState("");
  const {strategy} = useParams();


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
      {/* <th>Hotel ID</th> */}
      <th>Hotel ID</th>
          <th>Hotel Name</th>
          <th>Hotel Address</th>    
          <th>Hotel City</th>    
          <th>Hotel Phone Number</th>    
          <th>Hotel Description</th>    
          <th>Hotel URL</th>    
    </tr>
  </thead>
  <tbody id="tbody">{tableRows}</tbody>
</table>
) : "Loading..."

return (    
  <div>
    <h2>Hotel List</h2>
    {!message && displayHotels}
    <p>{message}</p>

  </div>
  
);
}