import React from "react";
import styles from "./CardItem.module.css";

const CardItem = ({onTopUpClick, onTransferClick, onBlockClick, card}) => {
    let blockedColor = card.bankAccount.status === 'BLOCKED' ? styles.card__blocked : "";
    return (
        <div className={`card ${blockedColor}`}>
            <div className="card-img-top"/>
                <div className="card-body">
                    <h4 className="card-title">{card.name}</h4>
                    <p className="card-text">ID: {card.id}</p>
                    <p className="card-text">Balance: {card.bankAccount.balance}</p>
                    <p className="card-text">Status: {card.bankAccount.status}</p>
                    <div className="d-flex flex-row justify-content-between">
                        <button type="button" className={`${styles.btn__topUp} btn`} onClick={() => onTopUpClick(card.id)}>Top up</button>
                        <button type="button" className={`${styles.btn__transfer} btn`} onClick={() => onTransferClick(card.id)}>Transfer</button>
                        <button type="button" className={`${styles.btn__block} btn`} onClick={() => onBlockClick(card.id)}>Block</button>
                    </div>
                </div>
        </div>
    )
}

export default CardItem;