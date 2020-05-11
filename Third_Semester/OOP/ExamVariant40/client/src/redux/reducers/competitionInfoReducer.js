import CompetitionsAPI from "../services/CompetitionsAPI";

const FETCH_COMPETITION_STARTED="FETCH_COMPETITION_STARTED";
const FETCH_COMPETITION_FAILURE="FETCH_COMPETITION_FAILURE";
const FETCH_COMPETITION_SUCCESS="FETCH_COMPETITION_SUCCESS";

const initialState={
    competition:null,
    isLoading: false
}

function competitionInfoReducer(state=initialState, action) {
    switch(action.type) {
        case FETCH_COMPETITION_STARTED:
            return {
                ...state,
                isLoading: true,
                competition: null
            }
        case FETCH_COMPETITION_SUCCESS:
            return {
                ...state,
                isLoading: false,
                competition: action.payload
            }
        default:
            return state;
    }
}

function fetchCompetitionStartedAc() {
    return {type:FETCH_COMPETITION_STARTED};
}

function fetchCompetitionFailureAc() {
    return {type:FETCH_COMPETITION_FAILURE};
}

function fetchCompetitionSuccessAc(competitions) {
    return {type:FETCH_COMPETITION_SUCCESS, payload: competitions};
}

export function fetchCompetition(competitionId) {
    return (dispatch)=>{

        dispatch(fetchCompetitionStartedAc());

        CompetitionsAPI.getCompetitionInfo(competitionId)
            .then(response=>{
                let competitions = response.data;
                dispatch(fetchCompetitionSuccessAc(competitions));
            })
            .catch(e=>{
                dispatch(fetchCompetitionFailureAc());
            })
    }
}

export default competitionInfoReducer;
