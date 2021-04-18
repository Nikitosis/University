import logo from './logo.svg';
import React from "react";
import Navbar from "./components/navbar/Navbar";
import CardsPage from "./components/content/CardsPage";
import styles from "./App.module.css";
import {BrowserRouter, Route} from "react-router-dom";
import LoginPage from "./components/authentication/LoginPage";
import {connect} from "react-redux";
import PrivateRoute from "./utils/PrivateRoute";
import UserList from "./components/users/UserList";
import UserCardsPage from "./components/content/user-cards/UserCardsPage";

const App = props => {
  return (
      <BrowserRouter>
        <div className="App container-fluid h-100 p-0">
            <Navbar/>
            <div className={`${styles.wrapper} p-0`}>
                <PrivateRoute requiredRoles={["USER","ADMIN"]} exact path="/my-cards" component={CardsPage} userRoles={props.roles} isLogged={props.isLogged}/>
                <PrivateRoute requiredRoles={["ADMIN"]} exact path="/users" component={UserList} userRoles={props.roles} isLogged={props.isLogged}/>
                <PrivateRoute requiredRoles={["ADMIN"]} exact path="/users/:userId" component={UserCardsPage} userRoles={props.roles} isLogged={props.isLogged}/>
                <PrivateRoute nonAuthorised={true} exact path="/login" component={LoginPage} userRoles={props.roles} isLogged={props.isLogged}/>
            </div>
        </div>
      </BrowserRouter>
  );
}

function mapStateToProps(state){
    return{
        roles:state.loginReducer.roles,
        isLogged:state.loginReducer.isLogged,
    }
}

export default connect(mapStateToProps)(App);
