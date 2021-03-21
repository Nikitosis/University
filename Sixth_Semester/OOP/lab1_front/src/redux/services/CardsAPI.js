import axios from "axios";
import {URL} from "../../utils/UrlConstraints";
import {Card} from "react-bootstrap";

class CardsAPI {
    getMyCards() {
        debugger;
        return axios.get(`${URL}/user/credit-card`);
    }

    createCard(card) {
        debugger;
        return axios.post(`${URL}/credit-card`,
            card
        )
    }

    topUp(cardId, amount) {
        debugger;
        return axios.post(`${URL}/credit-card/top-up`,
            {
                cardId,
                amount
            }
        )
    }
}

export default new CardsAPI();