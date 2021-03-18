import logo from './logo.svg';
import React from "react";
import Navbar from "./navbar/Navbar";
import CardsPage from "./content/CardsPage";
import styles from "./App.module.css";

const App = () => {
  return (
    <div className="App container-fluid h-100 p-0">
        <Navbar/>
        <div className={` p-0 min-vh-100`}>
            <CardsPage/>
        </div>
    </div>
  );
}

export default App;
