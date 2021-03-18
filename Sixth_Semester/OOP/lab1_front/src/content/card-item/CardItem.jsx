import React from "react";
import styles from "./CardItem.module.css";

const CardItem = (props) => {
    return (
        <div className="card">
            <div className="card-img-top"/>
                <div className="card-body">
                    <h4 className="card-title">Card title</h4>
                    <p className="card-text">
                        Some quick example text to build on the card title
                        and make up the bulk of the card's content.
                    </p>
                    <div className="d-flex flex-row justify-content-between">
                        <a href="#" className={`${styles.btn__topUp} btn`}>Top up</a>
                        <a href="#" className={`${styles.btn__transfer} btn`}>Transfer</a>
                        <a href="#" className={`${styles.btn__block} btn`}>Block</a>
                    </div>
                </div>
        </div>
    )
}

export default CardItem;