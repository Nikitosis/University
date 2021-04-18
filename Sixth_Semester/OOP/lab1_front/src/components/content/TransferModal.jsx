import React from "react";
import Modal from "react-bootstrap/Modal";
import {Button} from "react-bootstrap";
import {
    closeCreateCardModal,
    closeTopUpModal, closeTransferModal,
    createCard,
    topUpCard,
    transfer
} from "../../redux/reducers/cardsReducer";
import {connect} from "react-redux";

class TransferModal extends React.Component{
    constructor(props){
        super(props);

        this.state={
            amount:"",
            cardToId:""
        }
    }

    setDefaultState(){
        this.setState(
            {
                amount:"",
                cardToId:""
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
            [event.target.name]:event.target.value,
        })
    }

    handleSave=()=>{
        let amount = this.state.amount;
        let cardTo = this.state.cardToId;
        let cardFrom = this.props.cardId;

        if(!/^\d+$/.test(amount)) {
            this.setState({
                amountValidationMessage:"Amount should be positive value"
            })

            return;
        }

        if(!/^\d+$/.test(cardTo)) {
            this.setState({
                cardToIdValidationMessage:"Card id must be positive value"
            })

            return;
        }

        this.props.onSave(
            cardFrom,
            cardTo,
            amount
        );
    }

    handleClose=()=>{
        this.props.onClose();
    }

    render() {
        let amountValidationStyle = this.state.amountValidationMessage==null ? "" : "is-invalid";
        let cardToIdValidationStyle = this.state.cardToIdValidationMessage==null ? "" : "is-invalid";

        return (
            <Modal show={this.props.show} onHide={this.handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Top up</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <form className="form">
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Amount to top up</label>
                            <input type="text" className={`form-control ${amountValidationStyle}`} placeholder="Amount" name="amount" value={this.state.amount} onChange={this.handleChange}/>
                            <small className="text-danger">
                                {this.state.amountValidationMessage}
                            </small>
                        </div>
                        <div className="form-group">
                            <label className={"font-weight-bold"}>Card to id</label>
                            <input type="text" className={`form-control ${cardToIdValidationStyle}`} placeholder="Card to id" name="cardToId" value={this.state.cardToId} onChange={this.handleChange}/>
                            <small className="text-danger">
                                {this.state.cardToIdValidationMessage}
                            </small>
                        </div>
                    </form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={this.handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={this.handleSave}>
                        Transfer
                    </Button>
                </Modal.Footer>
            </Modal>
        );
    }

}

function mapStateToProps(state){
    return {
        show:state.cardsReducer.isTransferModalOpened,
        cardId:state.cardsReducer.curCardId
    }
}

function mapDispatchToProps(dispatch){
    return{
        onSave:(cardFromId, cardToId, amount)=>dispatch(transfer(cardFromId, cardToId, amount)),
        onClose:()=>dispatch(closeTransferModal())
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(TransferModal);