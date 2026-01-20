

import './index.css';
import { Button, Typography,Box} from '@mui/material';
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
  console.log("ovo je email",email);

    const handleLoginClick = () => {
        window.location.href = `${import.meta.env.VITE_API_URL}` + '/oauth2/authorization/google';
    };

  return (
    <div style={{marginTop:"5%"}}>
      <Typography variant="h6"  sx={{color:'#0072e5'}}>
     Dobrodošli u hotel Modrila!
     </Typography>
    <Box sx={{marginTop:"2%",backgroundColor:"#c0c9d3", borderRadius:"15px",display:"flex",flexDirection:{md:"row",xs: "column"},justifyContent:"space-around",alignItems:"center"}}>

        {
          email && <Typography  variant="h6" component={Link} to="/reservation"className="link_reservation" >
     Rezervirajte odmah!
     </Typography>
        }
          {
          !email&&
          <Button variant="h6" sx={{color:'#0072e5'}} onClick={handleLoginClick}>
     Rezervirajte odmah!
     </Button>
     
        }
    
     <img style={{height:"auto",maxWidth:"100%"}} src="https://images.unsplash.com/photo-1618773928121-c32242e63f39?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aG90ZWx8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&q=60&w=600"></img>
           
    </Box>
    </div>
    
    
  );
}

export default HomePage;