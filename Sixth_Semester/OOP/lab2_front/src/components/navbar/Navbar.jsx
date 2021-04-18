import React from "react";
import styles from "./Navbar.module.css";
import {NavLink} from "react-router-dom";
import PrivateComponent from "../../utils/PrivateComponent";
import {executeLogin, executeLogout} from "../../redux/reducers/loginReducer";
import {connect} from "react-redux";
import {openRegistrationModal} from "../../redux/reducers/registrationReducer";
import RegistrationModal from "../registration/RegistrationModal";

const Navbar = (props) => {
    return (
        <nav className="navbar navbar-expand-md navbar-dark bg-primary">
            <ul className="nav">
                <li className="nav-item">
                    <PrivateComponent requiredRoles={["USER"]}>
                        <NavLink className={`nav-link ${styles.nav_link}`} to="/my-cards" activeClassName={styles.nav_link__active}>My cards</NavLink>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent requiredRoles={["ADMIN"]}>
                        <NavLink className={`nav-link ${styles.nav_link}`} to="/users" activeClassName={styles.nav_link__active}>Users</NavLink>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent nonAuthorised={true}>
                        <NavLink className={`nav-link ${styles.nav_link}`} to="/login" activeClassName={styles.nav_link__active}>Login</NavLink>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent requiredRoles={["USER","ADMIN"]}>
                        <a className={`nav-link ${styles.nav_link}`} href="#" onClick={props.logout}>Logout</a>
                    </PrivateComponent>
                </li>
                <li className="nav-item">
                    <PrivateComponent nonAuthorised={true}>
                        <a className={`nav-link ${styles.nav_link}`} href="#" onClick={props.openRegistrationModal}>Register</a>
                        <RegistrationModal/>
                    </PrivateComponent>
                </li>
            </ul>
        </nav>
    )
}

function mapDispatchToProps(dispatch){
    return{
        logout:()=>dispatch(executeLogout()),
        openRegistrationModal:()=>dispatch(openRegistrationModal())
    }
}

export default connect(null, mapDispatchToProps)(Navbar);