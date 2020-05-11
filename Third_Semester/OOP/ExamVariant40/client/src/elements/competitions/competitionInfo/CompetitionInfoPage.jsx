import React from "react";
import {fetchCompetitions} from "../../../redux/reducers/competitionsReducer";
import connect from "react-redux/lib/connect/connect";
import {fetchCompetition} from "../../../redux/reducers/competitionInfoReducer";
import StudentTab from "./StudentTab";
import SportsFieldTab from "./SportsFieldTab";
import CompetitionResultTab from "./CompetitionResultTab";
import {ClipLoader} from "react-spinners";

class CompetitionInfoPage extends React.Component{
    componentDidMount() {
        this.props.fetchCompetition(this.props.match.params.competitionId);
    }

    render() {
        if(this.props.isLoading || this.props.competition == null) {
            return <ClipLoader loading={true}/>;
        }

        return (
            <div>
                <div>
                    <h5>Name</h5>
                    <p>{this.props.competition.name}</p>
                </div>
                <div>
                    <h5>Date</h5>
                    <p>{this.props.competition.date}</p>
                </div>
                <div>
                    <h5>Type</h5>
                    <p>{this.props.competition.competitionType.name}</p>
                </div>
                <div>
                    <h5>Has finished: </h5>
                    <p>{this.props.competition.finished ? "Yes" : "No"}</p>
                </div>
                <div className>
                    <h5>Participants</h5>
                    <table className="table table-bordered">
                        <thead className={'thead-dark'}>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                            </tr>
                        </thead>
                        <tbody>
                        {this.props.competition.students
                            .map((student)=>(
                                <StudentTab student={student}/>
                            ))}
                        </tbody>
                    </table>
                </div>

                <div>
                    <h5>Sports fields</h5>
                    <table className="table table-bordered">
                        <thead className={'thead-dark'}>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {this.props.competition.sportsFields
                            .map((sportsField)=>(
                                <SportsFieldTab sportsField={sportsField}/>
                            ))}
                        </tbody>
                    </table>
                </div>

                {this.props.competition.finished &&
                    <div>
                        <h5>Results</h5>
                            <table className="table table-bordered">
                                <thead className={'thead-dark'}>
                                <tr>
                                    <th>Name</th>
                                    <th>Score</th>
                                </tr>
                                </thead>
                                <tbody>
                                {this.props.competition.competitionResults
                                    .map((competitionResults)=>(
                                        <CompetitionResultTab competitionResult={competitionResults}/>
                                    ))}
                                </tbody>
                            </table>
                    </div>
                }



            </div>
        );
    }
}

function mapStateToProps(state){
    return {
        competition:state.competitionInfoReducer.competition
    }
}

function mapDispatchToProps(dispatch){
    return {
        fetchCompetition:(competitionId)=>dispatch(fetchCompetition(competitionId))
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(CompetitionInfoPage);
