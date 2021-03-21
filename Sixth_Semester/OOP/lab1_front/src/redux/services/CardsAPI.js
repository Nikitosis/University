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
}

export default new CardsAPI();