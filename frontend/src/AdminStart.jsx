import { Button} from "@mui/material";
import './index.css';
import React, { useState } from "react";
import RoomEdit from "./AdminComponents/RoomEdit.jsx";
import Statistics from "./AdminComponents/Statistics.jsx";
import UserEdit from "./AdminComponents/UserEdit.jsx";
import { Link } from 'react-router-dom';

export default function AdminStart() {
  const [active, setActive] = useState("roomEdit");
  
  return(
    <div className="adminStartBody" style= {{display: "flex", flexWrap: "wrap", gap: "1rem", justifyContent: "space-between", height: "100%", minHeight: "100%", boxSizing: "border-box"}}>
      <div className="adminStartButtons">
        <Button variant="contained" onClick={() => setActive("roomEdit")} className={active === "roomEdit" ? "active" : ""}>Uredi sobe</Button>
        <Button variant="contained" onClick={() => setActive("statistics")} className={active === "statistics" ? "active" : ""}>Statistika</Button>
        <Button variant="contained" onClick={() => setActive("userEdit")} className={active === "userEdit" ? "active" : ""}>Uredi korisnike</Button>
      </div> 
      <div className="adminSeparator"/>
      <div className="adminStartContent">
        {active === "roomEdit" && <RoomEdit />}
        {active === "statistics" && <Statistics />}
        {active === "userEdit" && <UserEdit />}
      </div>
    </div>
  );
}