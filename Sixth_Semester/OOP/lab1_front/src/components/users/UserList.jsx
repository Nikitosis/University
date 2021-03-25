import React from "react";
import {connect} from "react-redux";
import {fetchUsers} from "../../redux/reducers/usersReducer";
import {NavLink} from "react-router-dom";
import styles from "../navbar/Navbar.module.css";
import PrivateComponent from "../../utils/PrivateComponent";

class UserList extends React.Component {

    componentDidMount() {
        this.props.fetchUsers();
        this.timer = setInterval(() => this.props.fetchUsers(), 60000);
    }

    onUserClick=(userId)=>{
        this.props.history.push("/users/"+userId);
    }

    render() {
        return (
            <div>
                <table className="table">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Username</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        this.props.users
                            .map((user) => (
                                    <tr onClick={()=>this.onUserClick(user.id)}>
                                        <th scope="row">{user.id}</th>
                                        <td>{user.firstName}</td>
                                        <td>{user.lastName}</td>
                                        <td>{user.username}</td>
                                    </tr>
                            ))
                    }
                    </tbody>
                </table>
            </div>
        )
    }
}

function mapStateToProps(state) {
    return {
        users: state.usersReducer.users
    }
}

function mapDispatchToProps(dispatch) {
    return {
        fetchUsers: () => dispatch(fetchUsers()),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(UserList);