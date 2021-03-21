import React from "react";
import styles from "./Navbar.module.css";
import {NavLink} from "react-router-dom";
import PrivateComponent from "../../utils/PrivateComponent";
import {executeLogin, executeLogout} from "../../redux/reducers/loginReducer";
import {connect} from "react-redux";

const Navbar = (props) => {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-primary">
            <ul className="nav">
                <li className="nav-item">
                    <PrivateComponent roles={["USER","ADMIN"]}>
                        <NavLink className={`nav-link ${styles.nav_link}`} to="/my-cards" activeClassName={styles.nav_link__active}>My cards</NavLink>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent roles={["ADMIN"]}>
                        <NavLink className={`nav-link ${styles.nav_link}`} to="/users" activeClassName={styles.nav_link__active}>Users</NavLink>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent nonAuthorised={true}>
                        <NavLink className={`nav-link ${styles.nav_link}`} to="/login" activeClassName={styles.nav_link__active}>Login</NavLink>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent roles={["USER","ADMIN"]}>
                        <a className={`nav-link ${styles.nav_link}`} onClick={props.logout}>Logout</a>
                    </PrivateComponent>
                </li>
            </ul>
        </nav>
    )
}

function mapDispatchToProps(dispatch){
    return{
        logout:()=>dispatch(executeLogout())
    }
}

export default connect(null, mapDispatchToProps)(Navbar);