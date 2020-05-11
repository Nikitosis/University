import React from "react";

const SportsFieldTab=({sportsField})=>{
    return(
        <tr>
            <th>{sportsField.id}</th>
            <td>{sportsField.name}</td>
        </tr>
    )
}

export default SportsFieldTab;
