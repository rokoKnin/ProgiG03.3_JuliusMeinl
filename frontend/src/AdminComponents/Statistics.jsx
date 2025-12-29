import React from 'react';
import { Chart as ChartJS } from 'chart.js/auto';
import { Line, Pie, Bar } from 'react-chartjs-2';

ChartJS.defaults.maintainAspectRatio = false;
ChartJS.defaults.responsive = true;

ChartJS.defaults.plugins.title.display = true;
ChartJS.defaults.plugins.title.align = 'center';
ChartJS.defaults.plugins.title.font.size = 16;
ChartJS.defaults.plugins.title.color = 'black';

export default function Statistics() {
    return (
        /*<div>
            <h2>Statistika</h2>
            <p>Ovdje će ići statistički podaci o korisnicima, rezervacijama i slično.</p>
        </div>*/
        <div className="Statistics" style= {{display: "flex", flexWrap: "wrap", gap: "1rem", justifyContent: "space-between", height: "100%", minHeight: "100%", boxSizing: "border-box"}}>
            <div className="dataCard" style={{width: "100%", height: "20rem", background: "white"}}>
                <Line
                    data={{
                        labels: ['Siječanj', 'Veljača', 'Ožujak', 'Travanj', 'Svibanj', 'Lipanj', 'Srpanj', 'Kolovoz', 'Rujan', 'Listopad', 'Studeni', 'Prosinac'],
                        datasets: [
                            {
                                label: 'Ukupano rezervacija',
                                data: [12, 19, 3, 5, 2, 3, 7, 10, 15, 20, 25, 30],
                            },
                            {
                               label: 'Dvo krevetne sobe',
                               data: [2, 3, 20, 5, 1, 4, 15, 8, 12, 18, 22, 28] 
                            },
                            {
                                label: 'Trokrevetne sobe',
                                data: [3, 10, 13, 15, 22, 30, 25, 18, 20, 25, 30, 40]
                            },
                            {
                                label: 'Penthouse sobe',
                                data: [5, 2, 3, 7, 12, 8, 10, 15, 18, 22, 28, 35]
                            }
                        ]
                    }} 
                    options={{
                        plugins: {
                            title: {
                                text: "Godišnji broj rezervacija"
                            }
                        }
                    }}/>
            </div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>
                <Line data= {{
                    labels : ['1.', '2.', '3.', '4.', '5.', '6.', '7.', '8.', '9.', '10.', '11.', '12.', '13.', '14.', '15.', '16.', '17.', '18.', '19.', '20.', '21.', '22.', '23.', '24.', '25.', '26.', '27.', '28.', '29.', '30.', '31.'],
                    datasets: [
                        {
                            label: 'Ukupno rezervacija po danima',
                            data: [12, 19, 3, 5, 2, 3, 7, 10, 15, 20, 25, 30, 12, 19, 3, 5, 2, 3, 7, 10, 15, 20, 25, 30, 12, 19, 3, 5, 2, 3, 7],
                        },
                        {
                            label: 'Dvo krevetne sobe',
                            data: [2, 3, 20, 5, 1, 4, 15, 8, 12, 18, 22, 28, 2, 3, 20, 5, 1, 4, 15, 8, 12, 18, 22, 28, 2, 3, 20, 5, 1, 4, 15] 
                        },
                        {
                            label: 'Trokrevetne sobe',
                            data: [3, 10, 13, 15, 22, 30, 25, 18, 20, 25, 30, 40, 3, 10, 13, 15, 22, 30, 25, 18, 20, 25, 30, 40]
                        },
                        {
                            label: 'Penthouse sobe',
                            data: [5, 2, 3, 7, 12, 8, 10, 15, 18, 22, 28, 35, 5, 2, 3, 7, 12, 8, 10, 15, 18, 22, 28, 35]
                        }   
                    ]
                }}
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
                <Pie data= {{
                    labels: ['Dvo krevetne sobe', 'Trokrevetne sobe', 'Penthouse sobe'],
                    datasets: [
                        {
                            data: [20, 30, 50],
                            backgroundColor: [
                                'rgba(255, 99, 132, 0.8)',
                                'rgba(54, 162, 235, 0.8)',
                                'rgba(75, 192, 192, 0.8)'
                            ]
                        }
                    ]
                }}
                options={{
                        plugins: {
                            title: {
                                text: "Postotak rezervacija po tipu sobe za ovaj mjesec"
                            }
                        }
                    }} />
            </div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>Graf 4</div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>Graf 5</div>
        </div>
    );
}
