import React from "react";

const StudentTab=({student})=>{
    return(
        <tr>
            <th>{student.id}</th>
            <td>{student.name}</td>
        </tr>
    )
}

export default StudentTab;
