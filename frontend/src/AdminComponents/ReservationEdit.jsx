/*import axios from "axios";
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
    const [editingRoom, setEditingRoom] = useState(null);
    const [availableRooms, setAvailableRooms] = useState([]);
    const [loadingRooms, setLoadingRooms] = useState(false);

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
                    user: {
                        name: r.ime,
                        surname: r.prezime,
                        email: r.email,
                        userId: r.korisnikId  // ← dodaj ovo
                    },
                    rooms: (r.sobe && r.sobe.length)
                        ? r.sobe
                        : [{ roomNumber: "N/A", roomType: "N/A", roomId: "N/A", datumOd: null, datumDo: null }],  // ← fallback
                    additionalContents: (r.sadrzaji && r.sadrzaji.length)
                        ? r.sadrzaji
                        : [{ content: "N/A", contentId: "N/A" }],  // ← fallback
                    paymentStatus: r.placeno ? "PAID" : "UNPAID",
                    dateFrom: (r.sobe && r.sobe.length) ? r.sobe[0].datumOd : null,
                    dateTo: (r.sobe && r.sobe.length) ? r.sobe[0].datumDo : null
                }));



                setReservations(mapped);
                setError(null);
            })
            .catch((error) => {
                console.error("Error fetching reservations:", error)
                setError("Greška prilikom učitavanja rezervacija.");
            })
            .finally(() => setLoading(false));
    }, []); // ✅ ZATVARANJE useEffect



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

    const fetchAvailableRooms = useCallback(async (dateFrom, dateTo, currentRoomId = null) => {
        setLoadingRooms(true);
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_API_URL}/api/reservations/available-rooms`,
                {
                    dateFrom,
                    dateTo,
                    currentRoomId 
                },
                { withCredentials: true }
            );
            
            setAvailableRooms(response.data || []);
        } catch (error) {
            console.error("Error fetching available rooms:", error);
            setAvailableRooms([]);
        } finally {
            setLoadingRooms(false);
        }
    }, []);

    const startEditRoom = useCallback((reservationId, roomIndex) => {
        const reservation = reservations.find(r => r.reservationId === reservationId);
        if (!reservation || !reservation.rooms[roomIndex]) return;
        
        const room = reservation.rooms[roomIndex];
        setEditingRoom({ reservationId, roomIndex });
        
        // Fetch available rooms for these dates
        if (room.datumOd && room.datumDo) {
            fetchAvailableRooms(room.datumOd, room.datumDo, room.roomId);
        }
    }, [reservations, fetchAvailableRooms]);

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
                    {filteredReservations.map((reservation) =>
                        reservation.rooms.length
                            ? reservation.rooms.map((room, idx) => {
                                const additional = reservation.additionalContents[idx] || { content: "N/A", contentId: "N/A" };
                                return (
                                    <tr key={`${reservation.reservationId}-${idx}`} style={{ border: "1px solid #1976d2" }}>
                                        <td>{reservation.reservationId}</td>
                                        <td>{reservation.user.name || "N/A"}</td>
                                        <td>{reservation.user.surname || "N/A"}</td>
                                        <td>{reservation.user.userId || "N/A"}</td>
                                        <td>{room.datumOd ? new Date(room.datumOd).toLocaleDateString() : "N/A"}</td>
                                        <td>{room.datumDo ? new Date(room.datumDo).toLocaleDateString() : "N/A"}</td>
                                        <td>{room.roomNumber || "N/A"}</td>
                                        <td>{room.roomType || "N/A"}</td>
                                        <td>{room.roomId || "N/A"}</td>
                                        <td>{additional.content}</td>
                                        <td>{additional.contentId}</td>
                                        <td>{reservation.paymentStatus || "N/A"}</td>
                                    </tr>
                                );
                            })
                            : (
                                <tr key={reservation.reservationId} style={{ border: "1px solid #1976d2" }}>
                                    <td>{reservation.reservationId}</td>
                                    <td>{reservation.user.name || "N/A"}</td>
                                    <td>{reservation.user.surname || "N/A"}</td>
                                    <td>{reservation.user.userId || "N/A"}</td>
                                    <td colSpan="8" style={{ textAlign: "center" }}>Nema soba</td>
                                    <td colSpan="2" style={{ textAlign: "center" }}>Nema dodatnog sadržaja</td>
                                    <td>{reservation.paymentStatus || "N/A"}</td>
                                </tr>
                            )
                    )}
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
*/
import axios from "axios";
import { useEffect, useState, useCallback } from "react";

export default function ReservationEdit({ setExportHandler }) {
    const [reservations, setReservations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filters, setFilters] = useState({
        search: "",
        dateFrom: "",
        dateTo: "",
        payment: "ALL"
    });
    const [editingRoom, setEditingRoom] = useState(null); // { reservationId, roomIndex }
    const [availableRooms, setAvailableRooms] = useState([]);
    const [loadingRooms, setLoadingRooms] = useState(false);

    // Fetch all reservations
    useEffect(() => {
        fetchReservations();
    }, []);

    const fetchReservations = useCallback(async () => {
        setLoading(true);
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/api/reservations/all`,
                { withCredentials: true }
            );
            
            if (!Array.isArray(response.data)) {
                throw new Error("Backend ne vraća niz, već: " + typeof response.data);
            }
            
            const mapped = response.data.map(r => ({
                reservationId: r.id,
                user: {
                    name: r.ime,
                    surname: r.prezime,
                    email: r.email,
                    userId: r.korisnikId
                },
                rooms: (r.sobe && r.sobe.length)
                    ? r.sobe.map(room => ({
                        ...room,
                        roomNumber: room.roomNumber || room.brojSobe || "N/A",
                        roomType: room.roomType || room.vrstaSobe || "N/A",
                        roomId: room.roomId || room.sobaId || "N/A",
                        datumOd: room.datumOd || room.datumPocetka,
                        datumDo: room.datumDo || room.datumKraja,
                        originalData: { ...room } // Store original data for reference
                    }))
                    : [{ 
                        roomNumber: "N/A", 
                        roomType: "N/A", 
                        roomId: "N/A", 
                        datumOd: null, 
                        datumDo: null,
                        originalData: null 
                    }],
                additionalContents: (r.sadrzaji && r.sadrzaji.length)
                    ? r.sadrzaji
                    : [{ content: "N/A", contentId: "N/A" }],
                paymentStatus: r.placeno ? "PAID" : "UNPAID",
                totalPrice: r.ukupnaCijena || 0
            }));
            
            setReservations(mapped);
            setError(null);
        } catch (error) {
            console.error("Error fetching reservations:", error);
            setError("Greška prilikom učitavanja rezervacija.");
        } finally {
            setLoading(false);
        }
    }, []);

    // Set up export handler
    useEffect(() => {
        setExportHandler(() => exportReservations);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    // Fetch available rooms for editing
    const fetchAvailableRooms = useCallback(async (dateFrom, dateTo, currentRoomId = null) => {
        setLoadingRooms(true);
        try {
            const response = await axios.post(
                `${import.meta.env.VITE_API_URL}/api/reservations/available-rooms`,
                {
                    dateFrom,
                    dateTo,
                    currentRoomId // Include current room to show it as available too
                },
                { withCredentials: true }
            );
            
            setAvailableRooms(response.data || []);
        } catch (error) {
            console.error("Error fetching available rooms:", error);
            setAvailableRooms([]);
        } finally {
            setLoadingRooms(false);
        }
    }, []);

    // Start editing a room
    const startEditRoom = useCallback((reservationId, roomIndex) => {
        const reservation = reservations.find(r => r.reservationId === reservationId);
        if (!reservation || !reservation.rooms[roomIndex]) return;
        
        const room = reservation.rooms[roomIndex];
        setEditingRoom({ reservationId, roomIndex });
        
        // Fetch available rooms for these dates
        if (room.datumOd && room.datumDo) {
            fetchAvailableRooms(room.datumOd, room.datumDo, room.roomId);
        }
    }, [reservations, fetchAvailableRooms]);

    // Save room change
    const saveRoomChange = useCallback(async (reservationId, roomIndex, newRoomNumber) => {
        if (!newRoomNumber || newRoomNumber === "N/A") return;
        
        const selectedRoom = availableRooms.find(room => 
            room.roomNumber === newRoomNumber || 
            room.brojSobe === newRoomNumber
        );
        
        if (!selectedRoom) {
            alert("Odabrana soba nije pronađena");
            return;
        }
        
        try {
            // Update reservation on backend
            const response = await axios.put(
                `${import.meta.env.VITE_API_URL}/api/reservations/${reservationId}/room`,
                {
                    roomIndex,
                    newRoomId: selectedRoom.id || selectedRoom.sobaId,
                    newRoomNumber: selectedRoom.roomNumber || selectedRoom.brojSobe,
                    newRoomType: selectedRoom.roomType || selectedRoom.vrstaSobe
                },
                { withCredentials: true }
            );
            
            if (response.status === 200) {
                // Update local state
                setReservations(prev => prev.map(reservation => {
                    if (reservation.reservationId === reservationId) {
                        const updatedRooms = [...reservation.rooms];
                        updatedRooms[roomIndex] = {
                            ...updatedRooms[roomIndex],
                            roomNumber: selectedRoom.roomNumber || selectedRoom.brojSobe,
                            roomType: selectedRoom.roomType || selectedRoom.vrstaSobe,
                            roomId: selectedRoom.id || selectedRoom.sobaId
                        };
                        return { ...reservation, rooms: updatedRooms };
                    }
                    return reservation;
                }));
                
                setEditingRoom(null);
                setAvailableRooms([]);
                alert("Soba uspješno promijenjena!");
            }
        } catch (error) {
            console.error("Error updating room:", error);
            alert("Greška prilikom promjene sobe.");
        }
    }, [availableRooms]);

    // Cancel editing
    const cancelEdit = useCallback(() => {
        setEditingRoom(null);
        setAvailableRooms([]);
    }, []);

    // Export function
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

    // Filter reservations
    const filteredReservations = Array.isArray(reservations) ? reservations.filter((reservation) => {
        if (!reservation) return false;

        const userName = reservation.user?.name || "";
        const userSurname = reservation.user?.surname || "";
        const reservationId = reservation.reservationId?.toString() || "";

        const matchSearch = 
            userName.toLowerCase().includes(filters.search.toLowerCase()) ||
            userSurname.toLowerCase().includes(filters.search.toLowerCase()) ||
            reservationId.includes(filters.search);
        
        // Check if any room matches the date filters
        const hasMatchingRoom = reservation.rooms?.some(room => {
            const roomDateFrom = room.datumOd;
            const roomDateTo = room.datumDo;
            
            const matchDateFrom = filters.dateFrom ? 
                new Date(roomDateFrom) >= new Date(filters.dateFrom) : true;
            const matchDateTo = filters.dateTo ? 
                new Date(roomDateTo) <= new Date(filters.dateTo) : true;
            
            return matchDateFrom && matchDateTo;
        });
        
        const matchPayment = filters.payment === "ALL" || reservation.paymentStatus === filters.payment;

        return matchSearch && (reservation.rooms?.length > 0 ? hasMatchingRoom : true) && matchPayment;
    }) : [];

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
            {/* Filters */}
            <div style={{ 
                display: "flex", 
                gap: "1rem",
                flexWrap: "wrap",
                padding: "0.5rem"
            }}>
                <input
                    type="text"
                    style={{ flexGrow: 1, minWidth: "100px", maxWidth: "200px", borderRadius: "5px", padding: "5px", fontFamily: "inherit"}}
                    placeholder="Pretraži po imenu ili ID-u"
                    value={filters.search}
                    onChange={(e) => setFilters({ ...filters, search: e.target.value })}
                />
                <div>
                    <strong style={{ marginRight: "5px" }}>Od:</strong>
                    <input
                        type="date"
                        style={{borderRadius: "5px", padding: "5px", minWidth: "100px", fontFamily: "inherit"}}
                        value={filters.dateFrom}
                        onChange={(e) => setFilters({ ...filters, dateFrom: e.target.value })}
                    />
                </div>
                <div>
                    <strong style={{ marginRight: "5px" }}>Do:</strong>
                    <input
                        type="date"
                        style={{borderRadius: "5px", padding: "5px", minWidth: "100px", fontFamily: "inherit"}}
                        value={filters.dateTo}
                        onChange={(e) => setFilters({ ...filters, dateTo: e.target.value })}
                    />
                </div>
                <select
                    style={{borderRadius: "5px", padding: "5px", minWidth: "80px", fontFamily: "inherit"}}
                    value={filters.payment}
                    onChange={(e) => setFilters({ ...filters, payment: e.target.value })}
                >
                    <option value="ALL">Svi</option>
                    <option value="PAID">Plaćeno</option>
                    <option value="UNPAID">Nije plaćeno</option>
                </select>
                
                {/* Refresh button */}
                <button
                    onClick={fetchReservations}
                    style={{
                        padding: "5px 15px",
                        backgroundColor: "#1976d2",
                        color: "white",
                        border: "none",
                        borderRadius: "5px",
                        cursor: "pointer",
                        fontFamily: "inherit"
                    }}
                >
                    Osvježi
                </button>
            </div>

            {/* Table */}
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
                            <th style={{border: "2px solid #1976d2", padding: "0px 10px"}}>Akcije</th>
                        </tr>
                    </thead>
                    <tbody>
                        {filteredReservations.map((reservation) =>
                            reservation.rooms.length
                                ? reservation.rooms.map((room, idx) => {
                                    const additional = reservation.additionalContents[idx] || { content: "N/A", contentId: "N/A" };
                                    const isEditing = editingRoom?.reservationId === reservation.reservationId && 
                                                     editingRoom?.roomIndex === idx;
                                    
                                    return (
                                        <tr key={`${reservation.reservationId}-${idx}`} style={{ border: "1px solid #1976d2" }}>
                                            <td>{reservation.reservationId}</td>
                                            <td>{reservation.user.name || "N/A"}</td>
                                            <td>{reservation.user.surname || "N/A"}</td>
                                            <td>{reservation.user.userId || "N/A"}</td>
                                            <td>{room.datumOd ? new Date(room.datumOd).toLocaleDateString() : "N/A"}</td>
                                            <td>{room.datumDo ? new Date(room.datumDo).toLocaleDateString() : "N/A"}</td>
                                            
                                            {/* Room Number Cell with Edit */}
                                            <td>
                                                {isEditing ? (
                                                    <div style={{ display: "flex", flexDirection: "column", gap: "5px" }}>
                                                        <select
                                                            style={{ width: "100%", padding: "3px" }}
                                                            disabled={loadingRooms}
                                                            onChange={(e) => {
                                                                if (e.target.value) {
                                                                    saveRoomChange(reservation.reservationId, idx, e.target.value);
                                                                }
                                                            }}
                                                        >
                                                            <option value="">Odaberi sobu</option>
                                                            {loadingRooms ? (
                                                                <option disabled>Učitavanje...</option>
                                                            ) : (
                                                                availableRooms.map(room => (
                                                                    <option key={room.id || room.sobaId} value={room.roomNumber || room.brojSobe}>
                                                                        {room.roomNumber || room.brojSobe} - {room.roomType || room.vrstaSobe}
                                                                    </option>
                                                                ))
                                                            )}
                                                        </select>
                                                        <button
                                                            onClick={cancelEdit}
                                                            style={{
                                                                padding: "2px 5px",
                                                                fontSize: "0.8em",
                                                                backgroundColor: "#f44336",
                                                                color: "white",
                                                                border: "none",
                                                                borderRadius: "3px",
                                                                cursor: "pointer"
                                                            }}
                                                        >
                                                            Otkaži
                                                        </button>
                                                    </div>
                                                ) : (
                                                    <div style={{ display: "flex", alignItems: "center", gap: "5px" }}>
                                                        {room.roomNumber || "N/A"}
                                                        <button
                                                            onClick={() => startEditRoom(reservation.reservationId, idx)}
                                                            style={{
                                                                padding: "2px 8px",
                                                                fontSize: "0.8em",
                                                                backgroundColor: "#4CAF50",
                                                                color: "white",
                                                                border: "none",
                                                                borderRadius: "3px",
                                                                cursor: "pointer"
                                                            }}
                                                            title="Promijeni sobu"
                                                        >
                                                            ✏️
                                                        </button>
                                                    </div>
                                                )}
                                            </td>
                                            
                                            <td>{room.roomType || "N/A"}</td>
                                            <td>{room.roomId || "N/A"}</td>
                                            <td>{additional.content}</td>
                                            <td>{additional.contentId}</td>
                                            <td>
                                                <span style={{
                                                    padding: "3px 8px",
                                                    borderRadius: "3px",
                                                    backgroundColor: reservation.paymentStatus === "PAID" ? "#4CAF50" : "#f44336",
                                                    color: "white",
                                                    fontSize: "0.9em"
                                                }}>
                                                    {reservation.paymentStatus || "N/A"}
                                                </span>
                                            </td>
                                            <td>
                                                <button
                                                    onClick={() => startEditRoom(reservation.reservationId, idx)}
                                                    style={{
                                                        padding: "5px 10px",
                                                        backgroundColor: "#1976d2",
                                                        color: "white",
                                                        border: "none",
                                                        borderRadius: "3px",
                                                        cursor: "pointer",
                                                        fontSize: "0.9em"
                                                    }}
                                                >
                                                    Promijeni sobu
                                                </button>
                                            </td>
                                        </tr>
                                    );
                                })
                                : (
                                    <tr key={reservation.reservationId} style={{ border: "1px solid #1976d2" }}>
                                        <td>{reservation.reservationId}</td>
                                        <td>{reservation.user.name || "N/A"}</td>
                                        <td>{reservation.user.surname || "N/A"}</td>
                                        <td>{reservation.user.userId || "N/A"}</td>
                                        <td colSpan="8" style={{ textAlign: "center" }}>Nema soba</td>
                                        <td colSpan="2" style={{ textAlign: "center" }}>Nema dodatnog sadržaja</td>
                                        <td>{reservation.paymentStatus || "N/A"}</td>
                                        <td></td>
                                    </tr>
                                )
                        )}
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