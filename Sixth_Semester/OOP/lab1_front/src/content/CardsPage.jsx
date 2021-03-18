import React from "react";
import styles from "./CardsPage.module.css";
import CardItem from "./card-item/CardItem";

const CardsPage = (props) => {
    return (
        <div className={`${styles.content} d-flex flex-column align-items-center vh-100`}>
            <div className={`${styles.content__card}`}>
                <CardItem/>
            </div>
            <div className={`${styles.content__card}`}>
                <CardItem/>
            </div>
            <div className={`${styles.content__card}`}>
                <CardItem/>
            </div>
        </div>
    )
}

export default CardsPage;