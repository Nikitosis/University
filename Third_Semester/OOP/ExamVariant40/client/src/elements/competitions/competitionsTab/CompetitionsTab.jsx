import React from 'react';
import styles from "./CompetitionsTab.module.css";
import {withRouter} from "react-router-dom";

class CompetitionsTab extends React.Component{

    onClicked=()=>{
        this.props.history.push("/competitions/"+this.props.competition.id);
    }

    render() {
        let colorStyle= this.props.competition.finished ? styles.finished : styles.notFinished;
        return(
            <tr className={`${colorStyle} ${styles.clickable}`} onClick={this.onClicked}>
                <th>{this.props.competition.id}</th>
                <td>{this.props.competition.name}</td>
                <td>{this.props.competition.date}</td>
                <td>{this.props.competition.competitionType.name}</td>
            </tr>
        )
    }
}

export default withRouter(CompetitionsTab);
