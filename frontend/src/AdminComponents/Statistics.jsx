import React, { useEffect, useState } from 'react';
import { Chart as ChartJS } from 'chart.js/auto';
import { Line, Pie } from 'react-chartjs-2';
import axios from 'axios';

ChartJS.defaults.maintainAspectRatio = false;
ChartJS.defaults.responsive = true;

ChartJS.defaults.plugins.title.display = true;
ChartJS.defaults.plugins.title.align = 'center';
ChartJS.defaults.plugins.title.font.size = 16;
ChartJS.defaults.plugins.title.color = 'black';

export default function Statistics({ setExportHandler }) {
    const [stats, setStats] = useState(null);

    const generateColors = (length) => {
        const baseColors = [
            'rgba(255, 99, 132, 0.8)',
            'rgba(54, 162, 235, 0.8)',
            'rgba(75, 192, 192, 0.8)',
            'rgba(255, 206, 86, 0.8)',
            'rgba(153, 102, 255, 0.8)',
        ];
        return Array.from({ length }, (_, i) => baseColors[i % baseColors.length]);
    };

    useEffect(() => {
        axios.get(`${import.meta.env.VITE_API_URL}/api/statistics`, { withCredentials: true })
            .then(res => setStats(res.data))
            .catch(err => console.error(err));
    }, []);

    useEffect(() => {
        setExportHandler(() => exportStatistics);
        return () => setExportHandler(null);
    }, [setExportHandler, stats]);


    const exportStatistics = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/api/statistics/export?format=${format}`,
                { withCredentials: true, responseType: 'blob' }
            );
            downloadFile(response.data, `statistics.${format}`);
        } catch (error) {
            console.error("Error exporting statistics: ", error);
        }
    };

    if (!stats) return <div>Loading statistics...</div>;

    const countryColors = generateColors(stats.country.name.length);
    const cityColors = generateColors(stats.city.name.length);

    // === Yearly line chart ===
    const yearlyLineData = {
        labels: stats.yearly.month.map(m => `${m}`),
        datasets: [
            { label: 'Ukupno rezervacija', data: stats.yearly.total },
            { label: 'Dvokrevetna King', data: stats.yearly.DVOKREVETNA_KING },
            { label: 'Dvokrevetna Twin', data: stats.yearly.DVOKREVETNA_TWIN },
            { label: 'Trokrevetna', data: stats.yearly.TROKREVETNA },
            { label: 'Penthouse', data: stats.yearly.PENTHOUSE },
        ]
    };

    // === Monthly line chart ===
    const monthlyLineData = {
        labels: stats.monthly.day.map(d => `${d}`),
        datasets: [
            { label: 'Ukupno rezervacija', data: stats.monthly.total },
            { label: 'Dvokrevetna King', data: stats.monthly.DVOKREVETNA_KING },
            { label: 'Dvokrevetna Twin', data: stats.monthly.DVOKREVETNA_TWIN },
            { label: 'Trokrevetna', data: stats.monthly.TROKREVETNA },
            { label: 'Penthouse', data: stats.monthly.PENTHOUSE },
        ]
    };

    // === Monthly pie chart ===
    const monthlyPieData = {
        labels: ['Dvokrevetna King', 'Dvokrevetna Twin', 'Trokrevetna', 'Penthouse'],
        datasets: [
            {
                data: [
                    stats.monthlyPie.DVOKREVETNA_KING,
                    stats.monthlyPie.DVOKREVETNA_TWIN,
                    stats.monthlyPie.TROKREVETNA,
                    stats.monthlyPie.PENTHOUSE
                ],
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(75, 192, 192, 0.8)',
                    'rgba(255, 206, 86, 0.8)',
                ]
            }
        ]
    };

    // === Monthly extra content pie chart ===
    const monthlyExtraPie = {
        labels: ['Bazen', 'Teretana', 'Restoran'],
        datasets: [
            {
                data: [
                    stats.monthlyExtra.BAZEN,
                    stats.monthlyExtra.TERETANA,
                    stats.monthlyExtra.RESTORAN
                ],
                backgroundColor: [
                    'rgba(153, 102, 255, 0.8)',
                    'rgba(255, 159, 64, 0.8)',
                    'rgba(199, 199, 199, 0.8)',
                ]
            }
        ]
    };

    // === Country pie chart ===
    const countryPieData = {
        labels: stats.country.name,
        datasets: [{ data: stats.country.data, backgroundColor: countryColors, borderWidth: 1, borderColor: 'white' }]
    };

    // === County pie chart ===
    const cityPieData = {
        labels: stats.city.name,
        datasets: [{ data: stats.city.data, backgroundColor: cityColors, borderWidth: 1, borderColor: 'white' }]
    };



    return (
        <div className="Statistics" style={{ display: "flex", flexWrap: "wrap", gap: "1rem", justifyContent: "space-between", height: "100%", minHeight: "100%", boxSizing: "border-box" }}>
            <div className="dataCard" style={{ width: "100%", height: "20rem", background: "white" }}>
                <Line data={yearlyLineData} options={{ plugins: { title: { text: "Godišnji broj rezervacija" } } }} />
            </div>
            <div className="dataCard" style={{ width: "100%", height: "20rem", background: "white" }}>
                <Line data={monthlyLineData} options={{ plugins: { title: { text: "Mjesečni broj rezervacija" } } }} />
            </div>
            <div className="dataCard" style={{ width: "45%", height: "20rem", background: "white" }}>
                <Pie data={monthlyPieData} options={{ plugins: { title: { text: "Postotak rezervacija po tipu sobe za ovaj mjesec" } } }} />
            </div>
            <div className="dataCard" style={{ width: "45%", height: "20rem", background: "white" }}>
                <Pie data={monthlyExtraPie} options={{ plugins: { title: { text: "Postotak korištenja dodatnih sadržaja za ovaj mjesec" } } }} />
            </div>
            <div className="dataCard" style={{ width: "45%", height: "20rem", background: "white" }}>
                <Pie data={countryPieData} options={{ plugins: { title: { text: "Postotak rezervacija po državama" } } }} />
            </div>
            <div className="dataCard" style={{ width: "45%", height: "20rem", background: "white" }}>
                <Pie data={cityPieData} options={{ plugins: { title: { text: "Postotak rezervacija po gradovima" } } }} />
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