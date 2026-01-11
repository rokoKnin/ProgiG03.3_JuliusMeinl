import axios from "axios";
import { use } from "react";

export default function ReservationEdit( { setExportHandler}) {
    const [reservations, setReservetions] = useState([]);
    const [selectedReservation, setSelectedReservation] = useState(null);
    const [filters, setFilters] = useState({
        search: "",
        dateFrom: "",
        dateTo: "",
        payment: "ALL"
    });

    useEffect(() =>  {
        axios
            .get(`${import.meta.env.VITE_BACKEND_URL}` + `/reservations`, { withCredentials: true })
            .then((response) => setReservetions(response.data))
            .catch((error) => console.error("Error fetching reservations:", error));
    }, []);

    useEffect(() => {
        setExportHandler(() => exportReservations);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportReservations = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_BACKEND_URL}/reservations/export?format=${format}`,
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

    const filteredReservations = reservations.filter((reservation) => {
        const matchSearch = 
            reservation.user.name.toLowerCase().includes(filters.search.toLowerCase()) ||
            reservation.reservationId.toString().includes(filters.search);
        const matchDateFrom = filters.dateFrom ? new Date(reservation.dateFrom) >= new Date(filters.dateFrom) : true;
        const matchDateTo = filters.dateTo ? new Date(reservation.dateTo) <= new Date(filters.dateTo) : true;
        const matchPayment = filters.payment === "ALL" || reservation.paymentStatus === filters.payment;

        return matchSearch && matchDateFrom && matchDateTo && matchPayment;
    });
    
}