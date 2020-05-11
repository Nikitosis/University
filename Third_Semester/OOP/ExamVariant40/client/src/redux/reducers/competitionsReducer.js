import CompetitionsAPI from "../services/CompetitionsAPI";

const FETCH_COMPETITIONS_STARTED="FETCH_COMPETITIONS_STARTED";
const FETCH_COMPETITIONS_FAILURE="FETCH_COMPETITIONS_FAILURE";
const FETCH_COMPETITIONS_SUCCESS="FETCH_COMPETITIONS_SUCCESS";

const initialState={
    competitions:[],
    isLoading: false
}

function competitionsReducer(state=initialState, action) {
    switch(action.type) {
        case FETCH_COMPETITIONS_STARTED:
            return {
                ...state,
                competitions: [],
                isLoading: true
            }
        case FETCH_COMPETITIONS_SUCCESS:
            return {
                ...state,
                isLoading: false,
                competitions: action.payload
            }
        default:
            return state;
    }
}

function fetchCompetitionsStartedAc() {
    return {type:FETCH_COMPETITIONS_STARTED};
}

function fetchCompetitionsFailureAc() {
    return {type:FETCH_COMPETITIONS_FAILURE};
}

function fetchCompetitionsSuccessAc(competitions) {
    return {type:FETCH_COMPETITIONS_SUCCESS, payload: competitions};
}

export function fetchCompetitions(startDate,endDate,sportsFields) {
    return (dispatch)=>{
        debugger;
        dispatch(fetchCompetitionsStartedAc());

        CompetitionsAPI.getCompetitions(startDate, endDate, sportsFields)
            .then(response=>{
                debugger;
                let competitions = response.data;
                dispatch(fetchCompetitionsSuccessAc(competitions));
            })
            .catch(e=>{
                debugger;
                dispatch(fetchCompetitionsFailureAc());
            })
    }
}

export default competitionsReducer;
