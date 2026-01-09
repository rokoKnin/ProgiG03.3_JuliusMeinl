import React, { useEffect, useState } from 'react';
import { Chart as ChartJS } from 'chart.js/auto';
import { Line, Pie, Bar } from 'react-chartjs-2';
import axios from 'axios';

ChartJS.defaults.maintainAspectRatio = false;
ChartJS.defaults.responsive = true;

ChartJS.defaults.plugins.title.display = true;
ChartJS.defaults.plugins.title.align = 'center';
ChartJS.defaults.plugins.title.font.size = 16;
ChartJS.defaults.plugins.title.color = 'black';

export default function Statistics( { setExportHandler} ) {
    const [stats, setStats] = useState(null);
    useEffect(() => {
        axios
            .get(`${import.meta.env.VITE_API_URL}` + `/statistics`, { withCredentials: true })
            .then(response => setStats(response.data))
            .catch(error => console.error('Error fetching statistics data:', error));
    }, []); 

    useEffect(() => {
        setExportHandler(() => exportStatistics);
        return () => setExportHandler(null);
    }, [setExportHandler]);

    const exportStatistics = async (format) => {
        try {
            const response = await axios.get(
                `${import.meta.env.VITE_API_URL}/statistics/export?format=${format}`,
                {
                    withCredentials: true,
                    responseType: "blob",
                }
            );
            downloadFile(response.data, `statistics.${format}`);
        } catch (error) {
            console.error("Error exporting statistics: ", error);
        }
    };
    
    if (!stats) {
        return <div>Loading statistics...</div>;
    }

    const yearlyLineData = {
        labels: stats.yearly.month.map(month => `${month}`),
        datasets: [
            {
                label: 'Ukupano rezervacija',
                data: stats.yearly.total
            },
            {
                label: 'Dvokrevetne sobe',
                data: stats.yearly.double
            },
            {
                label: 'Trokrevetne sobe',
                data: stats.yearly.triple
            },
            {
                label: 'Penthouse sobe',
                data: stats.yearly.penthouse
            }
        ]
    };
    const monthlyLineData = {
        labels: stats.monthly.day.map(day => `${day}`),
        datasets: [
            {
                label: 'Ukupano rezervacija',
                data: stats.monthly.total
            },
            {
                label: 'Dvokrevetne sobe',
                data: stats.monthly.double
            },
            {
                label: 'Trokrevetne sobe',
                data: stats.monthly.triple
            },
            {
                label: 'Penthouse sobe',
                data: stats.monthly.penthouse
            }
        ]
    };
    const monthlyPieData = {
        labels: [
            'Dvokrevetne sobe',
            'Trokrevetne sobe',
            'Penthouse sobe'
        ],
        datasets: [
            {
                data: [
                    stats.monthlyPie.double,
                    stats.monthlyPie.triple,
                    stats.monthlyPie.penthouse
                ], 
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(75, 192, 192, 0.8)'
                ]
            }
        ]
    };
    const countryPieData = {
        labels: stats.country.name,
        datasets: [
            {
                data: [
                    stats.country.data
                ], 
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(75, 192, 192, 0.8)'
                ]
            }
        ]
    };
    const countyPieData = {
        labels: stats.county.name,
        datasets: [
            {
                data: [
                    stats.county.data
                ], 
                backgroundColor: [
                    'rgba(255, 99, 132, 0.8)',
                    'rgba(54, 162, 235, 0.8)',
                    'rgba(75, 192, 192, 0.8)'
                ]
            }
        ]
    }; 

    return (
        /*<div>
            <h2>Statistika</h2>
            <p>Ovdje će ići statistički podaci o korisnicima, rezervacijama i slično.</p>
        </div>*/
        
        <div className="Statistics" style= {{display: "flex", flexWrap: "wrap", gap: "1rem", justifyContent: "space-between", height: "100%", minHeight: "100%", boxSizing: "border-box"}}>
            <div className="dataCard" style={{width: "100%", height: "20rem", background: "white"}}>
                <Line
                    data={yearlyLineData}
                    options={{
                        plugins: {
                            title: {
                                text: "Godišnji broj rezervacija"
                            }
                        }
                    }}
                />
            </div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>
                <Line 
                    data= {monthlyLineData}
                    options={{
                        plugins: {
                            title: {
                                text: "Mjesečni broj rezervacija"
                            }
                        }
                    }}
                />
            </div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>
                <Pie data= {monthlyPieData}
                options={{
                        plugins: {
                            title: {
                                text: "Postotak rezervacija po tipu sobe za ovaj mjesec"
                            }
                        }
                    }} />
            </div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>
                <Pie data= {countryPieData}
                options={{
                        plugins: {
                            title: {
                                text: "Postotak rezervacija po državama"
                            }
                        }
                    }} />
            </div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>
                <Pie data= {countyPieData}
                options={{
                        plugins: {
                            title: {
                                text: "Postotak rezervacija po županijama"
                            }
                        }
                    }} />
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