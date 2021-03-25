import CardsAPI from "../services/CardsAPI";

const FETCH_CARDS_STARTED="FETCH_CARDS_STARTED";
const FETCH_CARDS_SUCCESS="FETCH_CARDS_SUCCESS";
const FETCH_CARDS_FAILURE="FETCH_CARDS_FAILURE";

const CREATE_CARDS_STARTED="CREATE_CARDS_STARTED";
const CREATE_CARDS_SUCCESS="CREATE_CARDS_SUCCESS";
const CREATE_CARDS_FAILURE="CREATE_CARDS_FAILURE";

const TOP_UP_STARTED="TOP_UP_STARTED";
const TOP_UP_SUCCESS="TOP_UP_SUCCESS";
const TOP_UP_FAILURE="TOP_UP_FAILURE";

const TRANSFER_STARTED="TRANSFER_STARTED";
const TRANSFER_SUCCESS="TRANSFER_SUCCESS";
const TRANSFER_FAILURE="TRANSFER_FAILURE";

const BLOCK_CARD_STARTED="BLOCK_CARD_STARTED";
const BLOCK_CARD_SUCCESS="BLOCK_CARD_SUCCESS";
const BLOCK_CARD_FAILURE="BLOCK_CARD_FAILURE";

const UNBLOCK_CARD_STARTED="UNBLOCK_CARD_STARTED";
const UNBLOCK_CARD_SUCCESS="UNBLOCK_CARD_SUCCESS";
const UNBLOCK_CARD_FAILURE="UNBLOCK_CARD_FAILURE";

const CLOSE_CREATE_CARD_MODAL="CLOSE_CREATE_CARD_MODAL";
const OPEN_CREATE_CARD_MODAL="OPEN_CREATE_CARD_MODAL";

const CLOSE_TOP_UP_MODAL="CLOSE_TOP_UP_MODAL";
const OPEN_TOP_UP_MODAL="OPEN_TOP_UP_MODAL";

const CLOSE_TRANSFER_MODAL="CLOSE_TRANSFER_MODAL";
const OPEN_TRANSFER_MODAL="OPEN_TRANSFER_MODAL";


const initialState={
    cards: [],
    loading: false,
    curCardId: null,
    isCreateCardModalOpened: false,
    isTopUpModalOpened: false,
    isTransferModalOpened: false
}

export default function cardsReducer(state=initialState, action){
    switch (action.type) {
        case OPEN_CREATE_CARD_MODAL:
            return {
                ...state,
                isCreateCardModalOpened: true,
            }
        case CLOSE_CREATE_CARD_MODAL:
            return {
                ...state,
                isCreateCardModalOpened: false,
            }
        case OPEN_TOP_UP_MODAL:
            return {
                ...state,
                isTopUpModalOpened: true,
                curCardId: action.payload.curCardId
            }
        case CLOSE_TOP_UP_MODAL:
            return {
                ...state,
                isTopUpModalOpened: false,
                curCardId: null
            }
        case OPEN_TRANSFER_MODAL:
            return {
                ...state,
                isTransferModalOpened: true,
                curCardId: action.payload.curCardId
            }
        case CLOSE_TRANSFER_MODAL:
            return {
                ...state,
                isTransferModalOpened: false,
                curCardId: null
            }
        case FETCH_CARDS_STARTED:
            return {
                ...state,
                cards: [],
                loading:true
            }
        case FETCH_CARDS_SUCCESS:
            debugger;
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

export function closeTopUpModal() {
    return {type: CLOSE_TOP_UP_MODAL}
}

export function openTopUpModal(curCardId) {
    return {type: OPEN_TOP_UP_MODAL,payload:{curCardId}}
}

export function topUpStarted() {
    return {type: TOP_UP_STARTED}
}

export function topUpSuccess() {
    return {type: TOP_UP_SUCCESS}
}

export function topUpFailure() {
    return {type: TOP_UP_FAILURE}
}

export function closeTransferModal() {
    return {type: CLOSE_TRANSFER_MODAL}
}

export function openTransferModal(curCardId) {
    return {type: OPEN_TRANSFER_MODAL,payload:{curCardId}}
}

export function transferStarted() {
    return {type: TRANSFER_STARTED}
}

export function transferSuccess() {
    return {type: TRANSFER_SUCCESS}
}

export function transferFailure() {
    return {type: TRANSFER_FAILURE}
}

export function blockCardStarted() {
    return {type: BLOCK_CARD_STARTED}
}

export function blockCardSuccess() {
    return {type: BLOCK_CARD_SUCCESS}
}

export function blockCardFailure() {
    return {type: BLOCK_CARD_FAILURE}
}

export function unblockCardStarted() {
    return {type: UNBLOCK_CARD_STARTED}
}

export function unblockCardSuccess() {
    return {type: UNBLOCK_CARD_SUCCESS}
}

export function unblockCardFailure() {
    return {type: UNBLOCK_CARD_FAILURE}
}

export function fetchCards(){
    return (dispatch,getState)=>{
        dispatch(fetchCardsStarted());
        CardsAPI.getMyCards()
            .then(res=> {
                    const cards=res.data;
                    dispatch(fetchCardsSuccess(cards));
                }
            )
            .catch((e)=>{
                dispatch(fetchCardsFailure());
            })
    };
}

export function fetchUserCards(userId) {
    return (dispatch,getState)=>{
        debugger;
        dispatch(fetchCardsStarted());
        CardsAPI.getUserCards(userId)
            .then(res=> {
                debugger;
                    const cards=res.data;
                    dispatch(fetchCardsSuccess(cards));
                }
            )
            .catch((e)=>{
                dispatch(fetchCardsFailure());
            })
    };
}

export function createCard(card){
    return (dispatch,getState)=>{
        dispatch(createCardStarted());
        CardsAPI.createCard(card)
            .then(res=> {
                    dispatch(createCardSuccess());
                    dispatch(fetchCards());
                    dispatch(closeCreateCardModal());
                }
            )
            .catch((e)=>{
                dispatch(createCardFailure());
            })
    };
}

export function topUpCard(cardId, amount){
    return (dispatch,getState)=>{
        dispatch(topUpStarted());
        CardsAPI.topUp(cardId, amount)
            .then(res=> {
                    dispatch(topUpSuccess());
                    dispatch(fetchCards());
                    dispatch(closeTopUpModal())
                }
            )
            .catch((e)=>{
                dispatch(topUpFailure());
            })
    };
}

export function transfer(cardFromId, cardToId, amount){
    return (dispatch,getState)=>{
        dispatch(transferStarted());
        CardsAPI.transfer(cardFromId, cardToId, amount)
            .then(res=> {
                    dispatch(transferSuccess());
                    dispatch(fetchCards());
                    dispatch(closeTransferModal())
                }
            )
            .catch((e)=>{
                dispatch(transferFailure());
            })
    };
}

export function blockCard(cardId){
    return (dispatch,getState)=>{
        dispatch(blockCardStarted());
        CardsAPI.blockCard(cardId)
            .then(res=> {
                    dispatch(blockCardSuccess());
                    dispatch(fetchCards());
                }
            )
            .catch((e)=>{
                dispatch(blockCardFailure());
            })
    };
}

export function unblockCard(cardId, userId){
    return (dispatch,getState)=>{
        dispatch(unblockCardStarted());
        CardsAPI.unblockCard(cardId)
            .then(res=> {
                debugger;
                    dispatch(unblockCardSuccess());
                    dispatch(fetchUserCards(userId))
                }
            )
            .catch((e)=>{
                dispatch(unblockCardFailure());
            })
    };
}


