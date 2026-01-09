import { Button} from "@mui/material";
import './index.css';
import React, { useState } from "react";
import RoomEdit from "./AdminComponents/RoomEdit.jsx";
import Statistics from "./AdminComponents/Statistics.jsx";
import UserEdit from "./AdminComponents/UserEdit.jsx";
import { Link } from 'react-router-dom';

export default function AdminStart() {
  const [active, setActive] = useState("roomEdit");
  const [exportHandler, setExportHandler] = useState(null);

  const handleExport = (format) => {
    if (exportHandler) {
      exportHandler(format);
    }
  };
  
  return(
    <div className="adminStartBody" 
      /*style= {{display: "flex", flexWrap: "wrap", gap: "1rem", justifyContent: "space-between", height: "100%", minHeight: "100%", boxSizing: "border-box"}}*/
      style = {{display: "flex", flexDirection: "column", gap: "1rem", height: "100%"}}>
      <div className="adminStartButtons">
        <Button variant="contained" onClick={() => setActive("roomEdit")} className={active === "roomEdit" ? "active" : ""}>Uredi sobe</Button>
        <Button variant="contained" onClick={() => setActive("statistics")} className={active === "statistics" ? "active" : ""}>Statistika</Button>
        <Button variant="contained" onClick={() => setActive("userEdit")} className={active === "userEdit" ? "active" : ""}>Uredi korisnike</Button>
      </div> 
      <div className="adminStartContent" style = {{flexGrow: 1, overflowY: "auto"}}>
        {active === "roomEdit" && <RoomEdit setExportHandler={setExportHandler}/>}
        {active === "statistics" && <Statistics setExportHandler={setExportHandler}/>}
        {active === "userEdit" && <UserEdit setExportHandler={setExportHandler}/>}
      </div>
      <div className = "adminExportButtons">
        <Button disabled={!exportHandler} 
          onClick={() => handleExport("pdf")}>PDF</Button>
        <Button disabled={!exportHandler || active === "statistics"} 
          onClick={() => handleExport("xml")}>XML</Button>
        <Button disabled={!exportHandler} 
          onClick={() => handleExport("xlsx")}>XLSX</Button>
      </div>
    </div>
  );
}