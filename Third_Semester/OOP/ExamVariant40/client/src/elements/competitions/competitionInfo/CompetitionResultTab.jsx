import React from "react";

const CompetitionResultTab=({competitionResult})=>{
    return(
        <tr>
            <td>{competitionResult.student.name}</td>
            <td>{competitionResult.totalScore}</td>
        </tr>
    )
}

export default CompetitionResultTab;
