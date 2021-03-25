import axios from "axios";
import {URL} from "../../utils/UrlConstraints";
import {Card} from "react-bootstrap";

class CardsAPI {
    getMyCards() {
        return axios.get(`${URL}/user/credit-card`);
    }

    getUserCards(userId) {
        let request = {
            userId:userId
        }
        return axios.post(`${URL}/specificUser/credit-cards`,
            request
        );
    }

    createCard(card) {
        return axios.post(`${URL}/credit-card`,
            card
        )
    }

    topUp(cardId, amount) {
        return axios.post(`${URL}/credit-card/top-up`,
            {
                cardId,
                amount
            }
        )
    }

    transfer(cardFromId, cardToId, amount) {
        return axios.post(`${URL}/credit-card/transfer`,
            {
                cardFromId,
                cardToId,
                amount
            }
        )
    }

    blockCard(cardId) {
        return axios.post(`${URL}/credit-card/block`,
            {
                id: cardId
            }
        )
    }

    unblockCard(cardId) {
        return axios.post(`${URL}/credit-card/unblock`,
            {
                id: cardId
            }
        )
    }
}

export default new CardsAPI();