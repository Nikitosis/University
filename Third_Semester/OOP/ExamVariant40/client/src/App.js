import React from 'react';
import logo from './logo.svg';
import './App.css';
import Header from "./elements/mainComponents/Header";
import CompetitionsPage from "./elements/competitions/CompetitionsPage";
import {BrowserRouter, Route} from "react-router-dom";
import CompetitionInfoPage from "./elements/competitions/competitionInfo/CompetitionInfoPage";

function App() {
  return (
      <BrowserRouter>
        <div className="App container-fluid d-flex flex-column">
            <Header/>
              <div className="content-wrapper flex-grow-1">
                  <Route exact path="/competitions" component={CompetitionsPage}/>
                  <Route exact path="/competitions/:competitionId" component={CompetitionInfoPage}/>
              </div>
        </div>
      </BrowserRouter>
  );
}

export default App;
