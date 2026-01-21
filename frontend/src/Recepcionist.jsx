import  {useState, useEffect } from "react";
import axios from "axios";
import { Button } from "@mui/material";
import ReservationEdit from "./AdminComponents/ReservationEdit";

export default function Recepcionist() {
    const [reservationExportHandler, setReservationExportHandler] = useState(null);

    const exportWorkSchedule = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/api/recepcionist/export?format=${format}`,
                { withCredentials: true, responseType: "blob", }
            );
            downloadFile(response.data, `plan_rada.${format}`);
        } catch (error) {
            console.error("Error exporting work schedule: ", error);
            alert("Greška prilikom izvoza plana rada.");
        }
    };

    const handleWorkScheduleExport = (format) => {
        exportWorkSchedule(format);
    };

    const handleReservationExport = (format) => {
        if (reservationExportHandler) {
            reservationExportHandler(format);
        }
    };

    return (
        <div style={{display: "flex", flexDirection: "column", gap: "1rem", height: "100%", alignItems: "center"}}>
            <Button variant="contained" style = {{margin: "1rem"}} onClick={() => handleWorkScheduleExport("pdf")}> Izvezi plan rada </Button>
            <ReservationEdit setExportHandler={setReservationExportHandler} />
            <div className = "recepcionistExportButtons">
                <Button disabled={!reservationExportHandler} 
                onClick={() => handleReservationExport("pdf")}>PDF</Button>
                <Button disabled={!reservationExportHandler} 
                onClick={() => handleReservationExport("xml")}>XML</Button>
                <Button disabled={!reservationExportHandler} 
                onClick={() => handleReservationExport("xlsx")}>XLSX</Button>
            </div>
        </div>
    );
}

function downloadFile(blob, filename) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
}