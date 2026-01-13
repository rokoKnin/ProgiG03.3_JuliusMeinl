import React, { useEffect, useState } from 'react';
import { Button, Select, MenuItem, Alert, CircularProgress } from "@mui/material";
import axios from 'axios';


export default function ExtraContentEdit( { setExportHandler}) {
    const [extra, setExtra] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        setLoading(true);
        axios
            .get(`${import.meta.env.VITE_API_URL}}` + `/extraContentEdit`, { withCredentials: true })
            .then((response) => {
                setReservations(response.data);
                setError(null);
            })
            .catch((error) => {
                console.error("Error fetching extra content:", error)
                setError("Greška prilikom učitavanja dodatnog sadržaja.");
            })
            .finally(() => {setLoading(false)});
    }, []);

    useEffect(() => {
        setExportHandler(() => exportExtraContent);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportExtraContent = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}}/extraContentEdit/export?format=${format}`,
                { withCredentials: true, responseType: "blob", }
            );
            downloadFile(response.data, `extraContent.${format}`);
        } catch (error) {
            console.error("Error exporting extra content: ", error);
            alert("Greška prilikom izvoza dodatnog sadržaja.");
        }
    };

    if (loading) return <div style={{ textAlign: 'center', marginTop: '2rem' }}><CircularProgress /></div>;
    if (error) return <Alert severity="error">{error}</Alert>;

    return (
        <div>

        </div>
    )
}

function downloadFile(blob, filename) {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement("a");
    a.href = url;
    a.download = filename;
    a.click();
    window.URL.revokeObjectURL(url);
}
