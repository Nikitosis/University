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
        this.setState({
            [event.target.name]:event.target.value,
        })
    }

    handleSave=()=>{
        let amount = this.state.amount;
        if(!/^\d+$/.test(amount)) {
            this.setState({
                amountValidationMessage:"Amount should be positive value"
            })

            return;
        }
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
        let amountValidationStyle = this.state.amountValidationMessage==null ? "" : "is-invalid";
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