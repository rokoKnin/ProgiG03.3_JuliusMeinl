import React, { useEffect } from 'react';
import {Box, colors, TextField} from '@mui/material';
import axios from 'axios' ;

function Profil() {
    const [email,setEmail]=React.useState(localStorage.getItem("email"));
    const [user,setUser]=React.useState("");
    
   
     async function postUser(){
      try {
                   const response= await axios.post(`${import.meta.env.VITE_API_URL}`+'/api/profile/' + `${email}`, email,  {withCredentials: true} )
                  setUser(response.data);
                  console.log(response.data);
                   return true;
                
                } catch (error) {
                  
                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
                   console.log(error.response.headers)
                   console.log(error.response.status)
                    return false;
                  }
            
    }
    useEffect(()=>{
    if(email){
    postUser();
    
}},
[])
    return(
        <Box sx={{display:"flex",flexDirection:"column",padding:"5%",backgroundColor:"#66b2ff",borderRadius:"15px",gap:1}}>
            <Box sx={{display:"flex",justifyContent:"space-evenly",flexDirection:"row"}}>
            <Box><span>Ime: </span>{user.name}</Box>
            <Box><span>Prezime: </span>{user.prezime}</Box>
            </Box>
            <Box sx={{display:"flex",justifyContent:"space-evenly",flexDirection:"row"}}>
            <Box ><span>Broj telefona: </span>{user.telefon}</Box>
            <Box><span>Email: </span>{user.email}</Box>
            </Box>
            <Box sx={{display:"flex",justifyContent:"space-evenly",flexDirection:"row"}}>
            <Box ><span>Grad: </span>{user.grad}</Box>
            <Box><span>Država: </span>{user.drzava}</Box>
            </Box>
        </Box>
    );
}

export default Profil;