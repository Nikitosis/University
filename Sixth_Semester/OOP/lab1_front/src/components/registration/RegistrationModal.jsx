import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {closeCreateCardModal, createCard} from "../../redux/reducers/cardsReducer";
import {connect} from "react-redux";
import {closeRegistrationModal, createUser} from "../../redux/reducers/registrationReducer";

class RegistrationModal extends React.Component{
    constructor(props){
        super(props);
    }

    state={
        firstName:"",
        lastName:"",
        username:""
    }

    setDefaultState(){
        this.setState(
            {
                firstName:"",
                lastName:"",
                username:""
            }
        )
    }

    //clear form, when it opens
    componentDidUpdate(prevProps, prevState, snapshot) {
        if(!prevProps.show && this.props.show){
            this.setDefaultState();
        }
    }

    handleChange=(event)=>{
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    handleSave=()=>{
        let userInfo={
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            username: this.state.username,
            password: this.state.password
        }
        this.props.onSave(userInfo);
    }


    handleClose=()=>{
        this.props.onClose();
    }

    render() {
        return (
            <Modal show={this.props.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Register user</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="form">
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Name</label>
                            <input type="text" className={`form-control`} placeholder="First name" name="firstName" value={this.state.firstName} onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Last name</label>
                            <input type="text" className={`form-control`} placeholder="Last name" name="lastName" value={this.state.lastName} onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Username</label>
                            <input type="text" className={`form-control`} placeholder="Username" name="username" value={this.state.username} onChange={this.handleChange}/>
                        </div>
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Password</label>
                            <input type="password" className={`form-control`} placeholder="Password" name="password" value={this.state.password} onChange={this.handleChange}/>
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={this.handleSave}>
                        Save
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

function mapStateToProps(state){
    return {
        show:state.registrationReducer.isRegistrationModalOpened
    }
}

function mapDispatchToProps(dispatch){
    return{
        onSave:(userInfo)=>dispatch(createUser(userInfo)),
        onClose:()=>dispatch(closeRegistrationModal())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(RegistrationModal);