import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {closeCreateCardModal, closeTopUpModal, createCard, topUpCard} from "../../redux/reducers/cardsReducer";
import {connect} from "react-redux";

class TopUpModal extends React.Component{
    constructor(props){
        super(props);

        this.state={
            amount:""
        }
    }

    setDefaultState(){
        this.setState(
            {
                amount:""
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

        if(!event.target.validity.valid) {
            this.setState({
                nameValidationMessage:"Only numbers are allowed"
            })
            return;
        }

        this.setState({
            [event.target.name]:event.target.value,
            nameValidationMessage:""
        })
    }

    handleSave=()=>{
        debugger;
        let amount = this.state.amount;
        let cardId = this.props.cardId;
        this.props.onSave(
            cardId,
            amount
        );
    }


    handleClose=()=>{
        this.props.onClose();
    }

    render() {
        debugger
        let nameValidationMessage = this.state.nameValidationMessage;
        return (
            <Modal show={this.props.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Top up</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="form">
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Amount to top up</label>
                            <input type="text" pattern="[0-9]*" className={`form-control`} placeholder="Amount" name="amount" value={this.state.amount} onChange={this.handleChange}/>
                            <small className="text-danger">
                                {nameValidationMessage}
                            </small>
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={this.handleSave}>
                        Top up
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

function mapStateToProps(state){
    return {
        show:state.cardsReducer.isTopUpModalOpened,
        cardId:state.cardsReducer.curCardId
    }
}

function mapDispatchToProps(dispatch){
    return{
        onSave:(cardId, amount)=>dispatch(topUpCard(cardId, amount)),
        onClose:()=>dispatch(closeTopUpModal())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(TopUpModal);