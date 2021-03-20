import logo from './logo.svg';
import React from "react";
import Navbar from "./navbar/Navbar";
import CardsPage from "./content/CardsPage";
import styles from "./App.module.css";
import {BrowserRouter, Route} from "react-router-dom";

const App = () => {
  return (
      <BrowserRouter>
        <div className="App container-fluid h-100 p-0">
            <Navbar/>
            <div className={`p-0 min-vh-100`}>
                <Route exact path={"/my-cards"} component={CardsPage}/>
            </div>
        </div>
      </BrowserRouter>
  );
}

export default App;
