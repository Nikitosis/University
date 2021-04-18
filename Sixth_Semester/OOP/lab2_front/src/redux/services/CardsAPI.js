import axios from "axios";
import {URL} from "../../utils/UrlConstraints";
import {Card} from "react-bootstrap";

class CardsAPI {
    getMyCards() {
        return axios.get(`${URL}/user/credit-cards`);
    }

    getUserCards(userId) {
        return axios.get(`${URL}/user/${userId}/credit-cards`);
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