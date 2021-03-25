import React from "react";
import styles from "./CardsPage.module.css";
import CardItem from "./card-item/CardItem";
import {
    blockCard,
    closeCreateCardModal,
    createCard,
    fetchCards,
    openCreateCardModal,
    openTopUpModal, openTransferModal
} from "../../redux/reducers/cardsReducer";
import {connect} from "react-redux";
import CreateCardModal from "./CreateCardModal";
import TopUpModal from "./TopUpModal";
import TransferModal from "./TransferModal";

class CardsPage extends React.Component {
    componentDidMount() {
        this.props.fetchCards();
        this.timer=setInterval(()=>this.props.fetchCards(),60000);
    }

    render() {
        return (
            <div>
                <div>
                    <button type="button" className={`${styles.button} btn btn-success`} onClick={this.props.openCreateCardModal}>+ Add card</button>
                </div>
                <div className={`${styles.content} d-flex flex-column align-items-center`}>
                {
                    this.props.cards
                        .map((card) => (
                            <div className={`${styles.content__card}`}>
                                <CardItem onTopUpClick={(cardId) => this.props.openTopUpModal(cardId)}
                                          onTransferClick={(cardId) => this.props.openTransferModal(cardId)}
                                          onBlockClick={(cardId) => this.props.blockCard(cardId)}
                                          card={card}/>
                            </div>
                        ))
                }
                </div>

                <CreateCardModal/>
                <TopUpModal/>
                <TransferModal/>
            </div>
        )
    }
}

function mapStateToProps(state){
    return {
        cards:state.cardsReducer.cards
    }
}

function mapDispatchToProps(dispatch){
    return{
        fetchCards:()=>dispatch(fetchCards()),
        createCard:()=>dispatch(createCard()),
        blockCard:(cardId)=>dispatch(blockCard(cardId)),
        openCreateCardModal:()=>dispatch(openCreateCardModal()),
        openTopUpModal:(cardId)=>dispatch(openTopUpModal(cardId)),
        openTransferModal:(cardId)=>dispatch(openTransferModal(cardId)),
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(CardsPage);