import React from 'react';

import {
  TextField,
  Button,
  Box,
  
 
} from '@mui/material';
import axios from 'axios' ;
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import { useEffect } from 'react';
import { useState } from 'react';
function Payment() {
    const navigate=useNavigate();
    const [email,setEmail]=React.useState(localStorage.getItem("email"));
    const[datumIsteka,setDatumIsteka]=useState("");
    const [cvv,setCvv]=useState();
    const [brojKartice,setBrojKartice]=useState("");
      useEffect(()=>{
        
      const privemail = localStorage.getItem('email');
      setEmail(privemail);
      },[])
    const handleSubmit=async(event)=>{
      event.preventDefault();
      
    if(cvv.length!=3){
      alert("Pogrešan format cvv-a.");
      return;
    }
    if(brojKartice.length!=16){
      
      alert("Pogrešan format broja kartice.");
      return;
    }
      if(datumIsteka.length!=7){
       
        alert("Pogrešan format datuma isteka kartice");
        return ;
      }
      else{
         if(datumIsteka.substring(2,3)!=='/'){
        alert("Pogrešan format datuma isteka kartice");
      return ;
      }
        const mjesec = parseInt(datumIsteka.substring(0, 2));
        const godina = parseInt( datumIsteka.substring(3, 7));
        let datumDanas=new Date();
        console.log(datumDanas)
        console.log(datumDanas.getFullYear()) 
        console.log(godina)
        if((mjesec<1 || mjesec>12)||parseInt(datumDanas.getFullYear())>godina||(datumDanas.getMonth()<mjesec&&datumDanas.getFullYear()===godina)){
        
        alert("Pogrešan datum");
        return ;
        }
      }
        const uspjeh=await postpay();
        if(uspjeh){navigate('/profil')}
    }

    const location=useLocation();
    const sadrzaj=location.state;
    async function postpay(){
      console.log(sadrzaj);
      try {
                   const response= await axios.post(`${import.meta.env.VITE_API_URL}`+'/api/reservations/' + `${email}`, sadrzaj,  {withCredentials: true} )
                  
                   return true;
                
                } catch (error) {
                  
                  console.log(sadrzaj)
                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
                   console.log(error.response.headers)
                   console.log(error.response.status)
                    return false;
                  }
            
    }
    return(
        <Box component="form" onSubmit={handleSubmit}>
        <Box sx={{display:"flex",flexDirection:"row",gap:"20px"}}>
            <TextField
          required
          id="brojKartice"
          label="Broj kartice"
          variant="outlined"
          type="number"
          helperText="Isključivo brojevi"
          onChange={(e)=>{setBrojKartice(e.target.value)}}
         sx={{ 
    '& input::-webkit-outer-spin-button, & input::-webkit-inner-spin-button': {
      display: 'none',
      margin: 0,
    },
    '& input[type=number]': {
      MozAppearance: 'textfield',
    }}}
        /><TextField
          required
          id="cvv"
          label="CVV"
          variant="outlined"
          type="number"
          helperText="Isključivo brojevi"
          
          onChange={(e)=>{setCvv(e.target.value)}}
          sx={{ 
    '& input::-webkit-outer-spin-button, & input::-webkit-inner-spin-button': {
      display: 'none',
      margin: 0,
    },
    '& input[type=number]': {
      MozAppearance: 'textfield',
    }}}
        /><TextField
          required
          id="datumIsteka"
          label="Datum isteka"
          variant="outlined"
          type="tel"          
          onChange={(e)=>{setDatumIsteka(e.target.value)}}
          helperText="Format MM/YYYY"
          sx={{ 
    '& input::-webkit-outer-spin-button, & input::-webkit-inner-spin-button': {
      display: 'none',
      margin: 0,
    },
    '& input[type=number]': {
      MozAppearance: 'textfield',
    }}}
        />
        </Box>
        <Box>
            
         <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="outlined" onClick={()=>{}}component={Link} to="/home">Odustani</Button>
         <Button type="submit" sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="contained">Spremi</Button>
        </Box>
        </Box>
    );
}

export default Payment;