import React from "react";
import styles from "./Navbar.module.css";
import {NavLink} from "react-router-dom";

const Navbar = (props) => {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-primary">
            <ul className="nav">
                <li className="nav-item">
                    <NavLink className={`nav-link ${styles.nav_link}`} to="/my-cards" activeClassName={styles.nav_link__active}>My cards</NavLink>
                </li>
                <li className="nav-item">
                    <NavLink className={`nav-link ${styles.nav_link}`} to="/users" activeClassName={styles.nav_link__active}>Users</NavLink>
                </li>
            </ul>
        </nav>
    )
}

export default Navbar;