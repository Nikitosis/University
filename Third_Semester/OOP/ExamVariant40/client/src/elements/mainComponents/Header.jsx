import React from "react";
import {NavLink} from "react-router-dom";

const Header=(props)=>{
    return(
        <div className="navbar navbar-expand-md  navbar-light bg-light">
            <ul className="navbar-nav m-auto">
                    <li className="nav-item"><NavLink to="/competitions" className="nav-link">Competitions</NavLink></li>
            </ul>
        </div>
    )
}

export default Header;
