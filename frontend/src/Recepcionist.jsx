import  {useState} from "react";
import React from "react";
import { Button } from "@mui/material";
import ReservationEdit from "./AdminComponents/ReservationEdit";

export default function Recepcionist() {
    const [exportHandler, setExportHandler] = React.useState(null);
    
    const handleExport = (format) => {
        if (exportHandler) {
            exportHandler(format);
        }
    };

    return (
        <div>
            <ReservationEdit setExportHandler={setExportHandler} />
            <div className = "adminExportButtons">
                <Button disabled={!exportHandler} 
                onClick={() => handleExport("pdf")}>PDF</Button>
                <Button disabled={!exportHandler} 
                onClick={() => handleExport("xml")}>XML</Button>
                <Button disabled={!exportHandler} 
                onClick={() => handleExport("xlsx")}>XLSX</Button>
            </div>
        </div>
    );
}