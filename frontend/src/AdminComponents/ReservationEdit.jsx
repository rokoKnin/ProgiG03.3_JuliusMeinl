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
            .get(`${import.meta.env.VITE_BACKEND_URL}` + `/reservationEdit`, { withCredentials: true })
            .then((response) => {
                setReservations(response.data);
                setError(null);
            })
            .catch((error) => {
                console.error("Error fetching reservations:", error)
                setError("Greška prilikom učitavanja rezervacija.");
            })
            .finally(() => {setLoading(false)});
    }, []);

    useEffect(() => {
        setExportHandler(() => exportReservations);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportReservations = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_BACKEND_URL}/reservationEdit/export?format=${format}`,
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

    const filteredReservations = (reservations || []).filter((reservation) => {
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
    });

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
                    placeholder="Pretraži po imenu ili ID-u"
                    value={filters.search}
                    onChange={(e) =>
                        setFilters({ ...filters, search: e.target.value })
                    }
                />
                <div>
                    <label style={{ marginRight: "5px" }}>Od:</label>
                    <input
                        type="date"
                        value={filters.dateFrom}
                        onChange={(e) =>
                            setFilters({ ...filters, dateFrom: e.target.value })
                        }
                    />
                </div>
                <div>
                    <label style={{ marginRight: "5px" }}>Do:</label>
                    <input
                        type="date"
                        value={filters.dateTo}
                        onChange={(e) =>
                            setFilters({ ...filters, dateTo: e.target.value })
                        }
                    />
                </div>
                <select
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
                        borderCollapse: "collapse",
                    }}
                >
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Ime korisnika</th>
                            <th>Prezime korisnika</th>
                            <th>ID korisnika</th>
                            <th>Datum od</th>
                            <th>Datum do</th>
                            <th>Vrsta sobe</th>
                            <th>ID sobe</th>
                            <th>Dodatan sadržaj</th>
                            <th>ID dodatnog sadržaja</th>
                            <th>Status plaćanja</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredReservations.map((reservation) => (
                            <tr key={reservation.reservationId}>
                                <td>{reservation.reservationId}</td>
                                <td>{reservation.user.name}</td>
                                <td>{reservation.user.surname}</td>
                                <td>{reservation.user.userId}</td>
                                <td>{new Date(reservation.dateFrom).toLocaleDateString()}</td>
                                <td>{new Date(reservation.dateTo).toLocaleDateString()}</td>
                                <td>{reservation.room.roomType}</td>
                                <td>{reservation.room.roomId}</td>
                                <td>{reservation.additionalContent.content}</td>
                                <td>{reservation.additionalContent.contentId}</td>
                                <td>{reservation.paymentStatus}</td>
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
