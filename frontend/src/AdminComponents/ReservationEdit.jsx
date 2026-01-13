import axios from "axios";
import { useEffect, useState } from "react";


export default function ReservationEdit( { setExportHandler}) {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({
        search: "",
        dateFrom: "",
        dateTo: "",
        payment: "ALL"
    });

    useEffect(() =>  {
        setLoading(true);
        axios
            .get(`${import.meta.env.VITE_API_URL }/api/reservations/all`, { withCredentials: true })
            .then((response) => {
                console.log("RAW RESPONSE:", response.data);
                if (!Array.isArray(response.data)) {
                    throw new Error("Backend ne vraća niz, već: " + typeof response.data);
                }
                const mapped = response.data.map(r => ({
                    reservationId: r.id,
                    user: r.korisnik,
                    rooms: r.sobe,
                    additionalContents: r.sadrzaji,
                    paymentStatus: r.placeno ? "PAID" : "UNPAID",
                    dateFrom: r.sobe.length ? r.sobe[0].datumOd : null,
                    dateTo: r.sobe.length ? r.sobe[0].datumDo : null
                }));
                setReservations(mapped);
                setError(null);
            })
            .catch((error) => {
                console.error("Error fetching reservations:", error)
                setError("Greška prilikom učitavanja rezervacija.");
            })
            .finally(() => setLoading(false));


        /*   axios
               .get(`${import.meta.env.VITE_API_URL}}` + `api/reservations/all`, { withCredentials: true })
               .then((response) => {
                   setReservations(response.data);
                   setError(null);
               })
               .catch((error) => {
                   console.error("Error fetching reservations:", error)
                   setError("Greška prilikom učitavanja rezervacija.");
               })
               .finally(() => {setLoading(false)});*/
    }, []);

    useEffect(() => {
        setExportHandler(() => exportReservations);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportReservations = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/api/reservations/export?format=${format}`,
                {
                    withCredentials: true,
                    responseType: "blob",
                }
            );
            downloadFile(response.data, `reservations.${format}`);
        } catch (error) {
            console.error("Error exporting reservations: ", error);
            alert("Greška prilikom izvoza rezervacija.");
        }
    };

    const filteredReservations = Array.isArray(reservations) ? reservations.filter((reservation) => {
        if (!reservation) return false;

        const userName = reservation.user?.name || "";
        const userSurname = reservation.user?.surname || "";
        const reservationId = reservation.reservationId?.toString() || "";

        const matchSearch = 
            userName.toLowerCase().includes(filters.search.toLowerCase()) ||
            userSurname.toLowerCase().includes(filters.search.toLowerCase()) ||
            reservationId.includes(filters.search);
        const matchDateFrom = filters.dateFrom ? new Date(reservation.dateFrom) >= new Date(filters.dateFrom) : true;
        const matchDateTo = filters.dateTo ? new Date(reservation.dateTo) <= new Date(filters.dateTo) : true;
        const matchPayment = filters.payment === "ALL" || reservation.paymentStatus === filters.payment;

        return matchSearch && matchDateFrom && matchDateTo && matchPayment;
    }): [];

    if (loading) return <div>Učitavanje rezervacija...</div>;
    if (error) return <div style={{ color: "red" }}>{error}</div>;

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            gap: "1rem",
            padding: "1rem",
            height: "100%",
        }}>
            <div style={{ 
                display: "flex", 
                gap: "1rem",
                flexWrap: "wrap",
                padding: "0.5rem"
            }}>
                <input
                    type="text"
                    style= {{ flexGrow: 1, minWidth: "100px", maxWidth: "200px", borderRadius: "5px", padding: "5px", fontFamily: "inherit"}}
                    placeholder="Pretraži po imenu ili ID-u"
                    value={filters.search}
                    onChange={(e) =>
                        setFilters({ ...filters, search: e.target.value })
                    }
                />
                <div>
                    <strong style={{ marginRight: "5px" }}>Od:</strong>
                    <input
                        type="date"
                        style={{borderRadius: "5px", padding: "5px", minWidth: "100px", fontFamily: "inherit"}}
                        value={filters.dateFrom}
                        onChange={(e) =>
                            setFilters({ ...filters, dateFrom: e.target.value })
                        }
                    />
                </div>
                <div>
                    <strong style={{ marginRight: "5px" }}>Do:</strong>
                    <input
                        type="date"
                        style={{borderRadius: "5px", padding: "5px", minWidth: "100px", fontFamily: "inherit"}}
                        value={filters.dateTo}
                        onChange={(e) =>
                            setFilters({ ...filters, dateTo: e.target.value })
                        }
                    />
                </div>
                <select
                    style={{borderRadius: "5px", padding: "5px", minWidth: "80px", fontFamily: "inherit"}}
                    value={filters.payment}
                    onChange={(e) =>
                        setFilters({ ...filters, payment: e.target.value })
                    }
                >
                    <option value="ALL">Svi</option>
                    <option value="PAID">Plaćeno</option>
                    <option value="UNPAID">Nije plaćeno</option>
                </select>
            </div>

            <div style={{
                flex: 1,
                overflowX: "auto",
                overflowY: "auto",

            }}>
                <table
                    style={{
                        width: "100%",
                        overflowX: "auto",
                        borderCollapse: "collapse",
                        border: "2px solid #1976d2",
                    }}
                >
                    <thead>
                        <tr style={{border: "2px solid #1976d2"}}>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>ID</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Ime korisnika</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Prezime korisnika</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>ID korisnika</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Datum od</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Datum do</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Broj sobe</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Vrsta sobe</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>ID sobe</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Dodatan sadržaj</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>ID dodatnog sadržaja</th>
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Status plaćanja</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredReservations.map((reservation) => (
                            <tr style={{border: "1px solid #1976d2"}} key={reservation.reservationId}>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.reservationId}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.user.name}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.user.surname}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.user.userId}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{new Date(reservation.dateFrom).toLocaleDateString()}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{new Date(reservation.dateTo).toLocaleDateString()}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.room.roomNumber}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.room.roomType}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.room.roomId}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.additionalContent.content}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.additionalContent.contentId}</td>
                                <td style={{border: "1px solid #1976d2", padding: "0px 10px"}}>{reservation.paymentStatus}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
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
