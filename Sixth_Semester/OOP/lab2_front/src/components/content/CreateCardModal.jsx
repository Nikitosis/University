import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {connect} from "react-redux";
import {closeCreateCardModal, createCard, fetchCards} from "../../redux/reducers/cardsReducer";

class CreateCardModal extends React.Component{
    constructor(props){
        super(props);
    }

    state={
        name:""
    }

    setDefaultState(){
        this.setState(
            {
                name:""
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
        let name = this.state.name;
        if(name === "") {
            this.setState(
                {
                    nameErrorMessage: "Name can't be empty"
                }
            );
            return;
        }

        this.props.onSave({
            name
        });
    }


    handleClose=()=>{
        this.props.onClose();
    }

    render() {
        let nameValidationStyle=this.state.nameErrorMessage==null ? "" : "is-invalid";
        return (
            <Modal show={this.props.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add card</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="form">
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Name</label>
                            <input type="text" className={`form-control ${nameValidationStyle}`} placeholder="Card name" name="name" value={this.state.name} onChange={this.handleChange}/>
                            <small className="text-danger">
                                {this.state.nameErrorMessage}
                            </small>
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
        show:state.cardsReducer.isCreateCardModalOpened
    }
}

function mapDispatchToProps(dispatch){
    return{
        onSave:(card)=>dispatch(createCard(card)),
        onClose:()=>dispatch(closeCreateCardModal())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(CreateCardModal);