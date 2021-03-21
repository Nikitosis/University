import React from "react";
import styles from "./CardItem.module.css";

const CardItem = ({onTopUpClick, card}) => {
    return (
        <div className="card">
            <div className="card-img-top"/>
                <div className="card-body">
                    <h4 className="card-title">{card.name}</h4>
                    <p className="card-text">ID: {card.id}</p>
                    <p className="card-text">Balance: {card.bankAccount.balance}</p>
                    <p className="card-text">Status: {card.bankAccount.status}</p>
                    <div className="d-flex flex-row justify-content-between">
                        <button type="button" className={`${styles.btn__topUp} btn`} onClick={() => onTopUpClick(card.id)}>Top up</button>
                        <a href="#" className={`${styles.btn__transfer} btn`}>Transfer</a>
                        <a href="#" className={`${styles.btn__block} btn`}>Block</a>
                    </div>
                </div>
        </div>
    )
}

export default CardItem;