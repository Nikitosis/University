import React from "react";
import styles from "./Navbar.module.css";

const Navbar = (props) => {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-primary">
            <ul className="nav">
                <li className="nav-item">
                    <a className={`nav-link ${styles.nav_link}`} href="#">My cards</a>
                </li>
                <li className="nav-item">
                    <a className={`nav-link ${styles.nav_link}`} href="#">Users</a>
                </li>
            </ul>
        </nav>
    )
}

export default Navbar;