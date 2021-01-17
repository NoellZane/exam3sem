import React, { useState,useEffect } from "react"
import facade from "./apiFacade";
import "./App.css"
import Home from "./components/Home";
import NoMatch from "./components/NoMatch";
import Register from "./components/Register";
import Fetch from "./components/Fetch";
import Admin from "./components/Admin";
import Booking from "./components/Booking";
import CustomerPage from "./components/CustomerPage.js";
import { LogIn, LoggedIn } from "./components/Login.js";

import {
  BrowserRouter as Router,
  Switch,
  Route,
  useLocation,
  Redirect,
  NavLink
} from "react-router-dom";
function Header({loggedIn, roles}){

  return(
      <ul className="header">
      <li><NavLink exact activeClassName="selected" to="/">Home</NavLink></li>
      {<li><NavLink activeClassName="active" to="/login">Log-in/out</NavLink></li>}
      {/* <li><NavLink activeClassName="selected" to="/login">Login</NavLink></li> */}
      {!loggedIn && <li><NavLink activeClassName="selected" to="/register">Register</NavLink></li>}
      {loggedIn && <li><NavLink activeClassName="selected" to="/customer">Customer Page</NavLink></li>}
      {loggedIn && <li><NavLink activeClassName="selected" to="/booking">Booking</NavLink></li>}
      {loggedIn &&<li><NavLink activeClassName="selected" to="/fetch/sequential">View Hotels Sequential</NavLink></li>}
      {loggedIn &&<li><NavLink activeClassName="selected" to="/fetch/parallel">View Hotels Parallel</NavLink></li>}
      {roles==='["admin"]' && <li><NavLink activeClassName="selected" to="/admin">Admin</NavLink></li>}
      </ul>
  );
  }

function App() {
  const [loggedIn, setLoggedIn] = useState(false)
  const [roles, setRoles] = useState([]);
  const logout = () => {    
    facade.logout()
    setLoggedIn(false)
    setRoles("");
   } 

  const login = (user, pass) => { 
    facade.login(user,pass,setRoles)
    .then(res =>setLoggedIn(true))
    .catch(err => {
      if (err.status) {
        err.fullError.then(e => {
          console.log(e.message)
          alert(e.message)
          })
      } 
      else { alert("Network error"); }
    })
   } 
 
    return(
      <div>
  <Header 
   loggedIn={loggedIn}
   setLoggedIn={setLoggedIn}
   roles={roles}
   setRoles={setRoles}
    />
  <Switch>
    <Route exact path="/">
      <Home />
    </Route>
    <Route path="/login">
    {!loggedIn ? (<LogIn login={login} />) :
   (<div>
   <LoggedIn roles={roles}/>
   <button onClick={logout}>Logout</button>
    </div>)}
    </Route>
    <Route path ="/register">
      <Register />
    </Route>
    <Route path ="/customer">
      <CustomerPage />
    </Route>
    <Route path ="/booking">
      <Booking />
    </Route>
    <Route path ="/fetch/:strategy">
      <Fetch />
    </Route>
    <Route>
      <Admin/>
    </Route>
    <Route>
      <NoMatch/>
    </Route>
  </Switch>
  </div>
    );
}

export default App;
