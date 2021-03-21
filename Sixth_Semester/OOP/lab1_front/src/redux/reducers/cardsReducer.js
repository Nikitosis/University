import CardsAPI from "../services/CardsAPI";

const FETCH_CARDS_STARTED="FETCH_CARDS_STARTED";
const FETCH_CARDS_SUCCESS="FETCH_CARDS_SUCCESS";
const FETCH_CARDS_FAILURE="FETCH_CARDS_FAILURE";

const CREATE_CARDS_STARTED="CREATE_CARDS_STARTED";
const CREATE_CARDS_SUCCESS="CREATE_CARDS_SUCCESS";
const CREATE_CARDS_FAILURE="CREATE_CARDS_FAILURE";

const CLOSE_CREATE_CARD_MODAL="CLOSE_CREATE_CARD_MODAL";
const OPEN_CREATE_CARD_MODAL="OPEN_CREATE_CARD_MODAL";

const initialState={
    cards: [],
    loading: false,
    isCreateCardModalOpened: false
}

export default function cardsReducer(state=initialState, action){
    switch (action.type) {
        case OPEN_CREATE_CARD_MODAL:
            return {
                ...state,
                isCreateCardModalOpened: true
            }
        case CLOSE_CREATE_CARD_MODAL:
            return {
                ...state,
                isCreateCardModalOpened: false
            }
        case CREATE_CARDS_SUCCESS:
            return {
                ...state,
                isCreateCardModalOpened: false
            }
        case FETCH_CARDS_STARTED:
            return {
                ...state,
                cards: [],
                loading:true
            }
        case FETCH_CARDS_SUCCESS:
            return{
                ...state,
                loading:false,
                cards: action.payload.cards
            };
        case FETCH_CARDS_FAILURE:
            return{
                ...state,
                loading:false
            }
        default:
            return state;
    }
}

export function fetchCardsStarted() {
    return {type: FETCH_CARDS_STARTED}
}

export function fetchCardsSuccess(cards) {
    return {type: FETCH_CARDS_SUCCESS, payload:{cards}}
}

export function fetchCardsFailure() {
    return {type: FETCH_CARDS_FAILURE}
}

export function createCardStarted() {
    return {type: CREATE_CARDS_STARTED}
}

export function createCardSuccess() {
    return {type: CREATE_CARDS_SUCCESS}
}

export function createCardFailure() {
    return {type: CREATE_CARDS_FAILURE}
}

export function closeCreateCardModal() {
    return {type: CLOSE_CREATE_CARD_MODAL}
}

export function openCreateCardModal() {
    return {type: OPEN_CREATE_CARD_MODAL}
}


export function fetchCards(){
    return (dispatch,getState)=>{
        debugger;
        dispatch(fetchCardsStarted());
        CardsAPI.getMyCards()
            .then(res=> {
                    debugger;
                    const cards=res.data;
                    dispatch(fetchCardsSuccess(cards));
                }
            )
            .catch((e)=>{
                debugger;
                dispatch(fetchCardsFailure());
            })
    };
}

export function createCard(card){
    return (dispatch,getState)=>{
        debugger;
        dispatch(createCardStarted());
        CardsAPI.createCard(card)
            .then(res=> {
                    dispatch(createCardSuccess());
                    dispatch(fetchCards());
                }
            )
            .catch((e)=>{
                debugger;
                dispatch(createCardFailure());
            })
    };
}

