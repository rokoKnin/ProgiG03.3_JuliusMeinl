import React, {useEffect, useState} from "react"
import axios from "axios";
import { Button} from "@mui/material";

import { Link, useNavigate } from 'react-router-dom';

const Dashboard = () => {
    const [user, setUser] = useState(null);
    const navigate = useNavigate();
    useEffect(() => {
        axios.get('http://localhost:8080/user-info', {withCredentials: true}).then(response =>
        { setUser(response.data);
        })
            .catch(error => console.error('Error ocurred', error))
    }, []);

    async function handleAddUser(){
        const ime = document.getElementById("firstname_id").value
        const prezime = document.getElementById("lastname_id").value
        const email = document.getElementById("email_id").value
        const telefon = document.getElementById("phone_id").value
        const nazDrzava = document.getElementById("country_id").value
        const nazMjesto = document.getElementById("place_id").value
        const postBr = document.getElementById("zipcode_id").value

        const userData = {
            ime,
            prezime,
            email,
            telefon,
            nazDrzava,
            nazMjesto,
            postBr
        }

        try {
            const response = await axios.post('http://localhost:8080/api/users', userData,  {withCredentials: true} )
            console.log('Success: Poslalo se sve', response.data)
            navigate("/")
        } catch (error) {
            alert('Niste ispunili sve podatke u potrebnom formatu!')
            console.error('Error: nije se poslao post zbog necega', error.response?.data)
        }
    }


    return(
        <div>
            <h2 style={{backgroundColor:"lightGrey", color:"#4d87c2ff", width:"400px",borderRadius:"10px", padding:"10px"}}>Dovršite postavljanje profila</h2>
            {user ? (
                <div>
                    <div>
                        <input type="text" placeholder="First name" id="firstname_id"  style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                        <input type="text" placeholder="Last name" id="lastname_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px"}}></input>
                    </div>
                    {/*<p> <strong>Email: </strong> {user.email}</p>*/}
                    <input type="text" placeholder="example@gmail.com" value={user.email} id="email_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                    <input type="text" placeholder="Phone number" id="phone_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px"}}></input>
                    <div>
                        <input type="text" placeholder="Drzava" id="country_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                        <input type="text" placeholder="Mjesto" id="place_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px",marginRight:"10px"}}></input>
                        <input type="text" placeholder="Poštanski broj" id="zipcode_id" style={{width:"200px",height:"30px",borderRadius:"10px",backgroundColor:"#b2c5e0ff",marginTop:"10px"}}></input>
                    </div>
                    <Button color="primary" variant="contained" sx={{marginTop: "10px"}} onClick={handleAddUser} > Confirm </Button>


                </div>
            ) : (
                <p> Loading user data...</p>
            )
            }
        </div>
    );
};

export default Dashboard;

