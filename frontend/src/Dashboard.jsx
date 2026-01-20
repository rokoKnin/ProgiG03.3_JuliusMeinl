import React, {useEffect, useState} from "react"
import axios from "axios";
import { Button} from "@mui/material";

import { Link, useNavigate } from 'react-router-dom';

const Dashboard = () => {

    const urlParams = new URLSearchParams(window.location.hash);

    let accessToken = localStorage.getItem("access_token");
    if (!accessToken) {
        accessToken = urlParams.get("access_token");
        if (accessToken) {
            localStorage.setItem("access_token", accessToken);
        }
    }

    let email = urlParams.get("email");

    const [ime, setIme] = useState(urlParams.get("ime"));
    const [prezime, setPrezime] = useState(urlParams.get("prezime"));
    const [telefon, setTelefon] = useState(urlParams.get("telefon"));
    const [lista,setLista]=useState([]);
    const navigate = useNavigate();
 async function handleAddUser(){
        const ime = document.getElementById("firstname_id").value
        const prezime = document.getElementById("lastname_id").value
        const telefon = document.getElementById("phone_id").value
        const nazivDrzave = document.getElementById("country_id").value
        const nazMjesto = document.getElementById("place_id").value
        const postBr = document.getElementById("zipcode_id").value

        const drzava = {
            nazivDrzave
        }

        const mjesto = {
            nazMjesto,
            postBr,
            drzava
        }
        
        const userData = {
            ime,
            prezime,
            email,
            telefon,
            mjesto
        }
        useEffect(()=>{
      
        const listaDrz=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/profile/countries-list', {withCredentials: true})
        .then(listaDrz =>
         { 
          setLista(listaDrz.data);
            console.log(listaDrz.data);
         })
             .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)})
     }, []);

        //console.log(userData)
        try {
            const response = await axios.put(`${import.meta.env.VITE_API_URL}` + '/api/users', userData,  {withCredentials: true} )
            console.log('Success: Poslalo se sve', response.data);   
            localStorage.setItem("email", response.data.email);
            localStorage.setItem("ime", response.data.ime);
            localStorage.setItem("prezime", response.data.prezime);
            localStorage.setItem("telefon", response.data.telefon);
            localStorage.setItem("ovlast", response.data.ovlast);
            navigate('/')
        } catch (error) {
            alert('Niste ispunili sve podatke u potrebnom formatu!')
            console.error('Error: nije se poslao post zbog necega', error.response?.data)
        }
    }


    return(
        <div>
            <h2 style={{backgroundColor:"lightGrey", color:"#4d87c2ff", width:"400px",borderRadius:"10px", padding:"10px"}}>Dovršite postavljanje profila</h2>
            <div>
                <div>
                    <input type="text" value={ime != null ? ime : ""} onChange={(e) => setIme(e.target.value)} placeholder="First name" id="firstname_id"  style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                    <input type="text" value={prezime != null ? prezime : ""} onChange={(e) => setPrezime(e.target.value)} placeholder="Last name" id="lastname_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px"}}></input>
                </div>
                {/*<p> <strong>Email: </strong> {user.email}</p>*/}
                {/*<input type="text" value={email} placeholder="example@gmail.com" id="email_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>*/}
                <div>
                <input type="number" value={telefon != null ? telefon : ""} onChange={(e) => setTelefon(e.target.value)} placeholder="Phone number" id="phone_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                <input type="text" placeholder="Drzava" id="country_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                </div>
                <div>
                    <input type="text" placeholder="Mjesto" id="place_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                    <input type="number" placeholder="Poštanski broj" id="zipcode_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px"}}></input>
                </div>
                <Button color="primary" variant="contained" sx={{marginTop: "10px"}} onClick={handleAddUser} > Confirm </Button>
            </div>
        </div>
    );
};

export default Dashboard;

