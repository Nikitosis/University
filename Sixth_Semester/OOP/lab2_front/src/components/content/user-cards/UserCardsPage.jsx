import React from "react";
import styles from "./CardsPage.module.css";
import CardItem from "../card-item/CardItem";
import {fetchUserCards, unblockCard} from "../../../redux/reducers/cardsReducer";
import {connect} from "react-redux";
import {withRouter} from "react-router-dom";

class UserCardsPage extends React.Component {
    componentDidMount() {
        debugger;
        this.props.fetchUserCards(this.props.match.params.userId);
        this.timer=setInterval(()=>this.props.fetchUserCards(this.props.match.params.userId),60000);
    }

    onUnblockCardClick = (cardId)=>{
        this.props.unblockCard(cardId, this.props.match.params.userId)
    }

    render() {
        debugger;
        return (
            <div>
                <div className={`${styles.content} d-flex flex-column align-items-center`}>
                {
                    this.props.cards
                        .map((card) => (
                            <div className={`${styles.content__card}`}>
                                <CardItem onUnblockClick={(cardId) => this.onUnblockCardClick(cardId)}
                                          card={card}/>
                            </div>
                        ))
                }
                </div>
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
        fetchUserCards:(userId)=>dispatch(fetchUserCards(userId)),
        unblockCard:(cardId, userId)=>dispatch(unblockCard(cardId, userId))
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(withRouter(UserCardsPage));