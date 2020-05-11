import React from "react";
import {fetchCompetitions} from "../../redux/reducers/competitionsReducer";
import connect from "react-redux/lib/connect/connect";
import CompetitionsTab from "./competitionsTab/CompetitionsTab";
import {ClipLoader} from "react-spinners";
import {NavLink} from "react-router-dom";

class CompetitionsPage extends React.Component{

    constructor(props){
        super(props);
        this.state={
            startDate:"2018-10-01 00:00:00",
            endDate:"2018-10-01 00:00:00",
            sportsFields:""
        };
    }

    handleChange=(event)=>{
        this.setState({
            [event.target.name]:event.target.value
        })
    }

    executeFilter=()=>{
        debugger;
        let parsedSportsFields=this.state.sportsFields.trim().split(" ");
        if(this.state.sportsFields == "") {
            parsedSportsFields = null;
        }
        this.props.fetchCompetitions(
            this.state.startDate,
            this.state.endDate,
            parsedSportsFields
        )
    }

    componentDidMount() {
        this.props.fetchCompetitions();
    }

    render() {
        if(this.props.isLoading) {
            return <ClipLoader loading={true}/>;
        }

        return (
            <div>
                <form className={"container-fluid row mb-4"} id="search" role="form">
                    <div className="col-md-3">
                        <ul className="nav nav-stacked">
                            <li><strong>From Date</strong></li>
                            <li>
                                <input type="text" className="form-control" value={this.state.startDate} name="startDate" onChange={this.handleChange}/>
                            </li>
                        </ul>
                    </div>

                    <div className="col-md-3">
                        <ul className="nav nav-stacked">
                            <li><strong>To Date</strong></li>
                            <li>
                                <input type="text" className="form-control" value={this.state.endDate} name="endDate" onChange={this.handleChange}/>
                            </li>
                        </ul>
                    </div>

                    <div className="col-md-3">
                        <ul className="nav nav-stacked">
                            <li><strong>Fields filter</strong></li>
                            <li>
                                <input type="text" className="form-control" value={this.state.sportsFields} name="sportsFields" onChange={this.handleChange}/>
                            </li>
                        </ul>
                    </div>

                    <div className="col-md-3 d-flex align-items-end">
                        <button className="btn btn-primary " type="button" onClick={this.executeFilter}>Search</button>
                    </div>
                </form>

                <table className={'table'}>
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Name</th>
                            <th>date</th>
                            <th>type</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.props.competitions
                            .map((competition)=>(
                                <CompetitionsTab competition={competition}/>
                            ))}
                    </tbody>
                </table>
            </div>
        )
    }
}

function mapStateToProps(state){
    return {
        competitions:state.competitionsReducer.competitions,
        isLoading:state.competitionsReducer.isLoading
    }
}

function mapDispatchToProps(dispatch){
    return {
        fetchCompetitions:(startDate, endDate, sportsFields)=>dispatch(fetchCompetitions(startDate, endDate, sportsFields))
    }
}

export default connect(mapStateToProps,mapDispatchToProps)(CompetitionsPage);
