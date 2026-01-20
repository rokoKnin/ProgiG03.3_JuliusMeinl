

import './index.css';
import { Button, Typography} from '@mui/material';
import { Link } from 'react-router-dom';
import React, { useEffect, useState } from "react";
import axios from "axios";
function HomePage() {
  const [user,setUser]=React.useState();
  const [email,setEmail]=React.useState();
  useEffect(()=>{
    
  const privemail = localStorage.getItem('email');
  setEmail(privemail);
  },[])
  // useEffect(() => {
   //          axios.get(`${import.meta.env.VITE_API_URL}` + '/api/users/info', {withCredentials: true}).then(response =>
   //          { setUser(response.data);
   //          })
   //              .catch(error => console.error('Error ocurred', error))
   //      }, []);

    const handleLoginClick = () => {
        window.location.href = `${import.meta.env.VITE_API_URL}` + '/api/oauth2/authorization/google';
    };

  return (
    <main style={{ padding: 16 }}>
      <h2 style={{ color: '#1976d2' }}>Dobrodošli u Modrila hotel</h2>
      <div className="home_container">
        {
          email && <Typography variant="h6" component={Link} to="/reservation"className="link_reservation">
     Rezervirajte odmah!
     </Typography>
        }
        {
          
          !email&&
          <div>
           {console.log("nema")}
          <Button variant="h6" onClick={handleLoginClick}>
            
     Rezervirajte odmah!
     </Button>
     </div>
        }
    
     <img src="https://images.unsplash.com/photo-1618773928121-c32242e63f39?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aG90ZWx8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&q=60&w=600"></img>
             </div>
    </main>
    
  );
}

export default HomePage;