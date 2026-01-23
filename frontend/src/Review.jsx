import Box from '@mui/material/Box';
import Rating from '@mui/material/Rating';
import { TextField } from '@mui/material';
import StarIcon from '@mui/icons-material/Star';
import React, {useEffect, useState} from "react"
import {Button, Typography} from '@mui/material';
import axios from "axios";
import { Link, useNavigate } from 'react-router-dom';
const labels = {
  1: 'Nezadovoljavajuće',
  2: 'Zadovoljavajuće',
  3: 'Dobro',
  4: 'Vrlo dobro',
  5: 'Odlično',
};
function getLabelText(value) {
    
  return `${value} Star${value !== 1 ? 's' : ''}, ${labels[value]}`;
}
export default function Review() {
    const navigate = useNavigate();
    const [value, setValue] = React.useState(2);
    const [hover, setHover] = React.useState(-1);
    const [komentar,setKomentar]=React.useState("");
    const [random,setRandom]=React.useState([]);
    const [korisnikovaRecenzija,setKorisnikovaRecenzija]=React.useState({ocjena:null});
    const [imaPravo,setImaPravo]=React.useState(true);
    const email = localStorage.getItem('email')
    const [average,setAverage]=useState(0);
       useEffect(()=>{
      
        const aveRec=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/reviews/average', {withCredentials: true})
        .then(aveRec =>
         { 
          setAverage(aveRec.data);
          console.log("average",aveRec.data)
         })
             .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)})
     }, []);
   useEffect(()=>{
      
        const korisnikRec=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/reviews/'+`${email}`, {withCredentials: true})
        .then(korisnikRec =>
         { 
          setKorisnikovaRecenzija(korisnikRec.data);
            console.log("ovo je korisnikova",korisnikRec.data);
         })
             .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)})
     }, []);
    useEffect(()=>{
      
        const randomL=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/reviews/random-reviews', {withCredentials: true})
        .then(randomL =>
         { 
          setRandom(randomL.data);
            console.log(randomL.data);
         })
             .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)})
     }, []);

    const handleSubmit=async(event)=>{
   
        event.preventDefault();
        const data={
            value,
            komentar,
            email
        }
        console.log(data);
         try {
                   const response= await axios.post(`${import.meta.env.VITE_API_URL}`+'/api/reviews/' + `${email}`, data,  {withCredentials: true} )
                   navigate("/").then(window.location.reload)
                   return true;

                } catch (error) {

                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)
                    return false;
                  }
    }
  return (
    <Box sx={{  display: 'flex',flexDirection:"column", }}>
        <Box >
      <Rating
        name="hover-feedback"
        value={value}
        precision={1}
       getLabelText={getLabelText}
        onChange={(event, newValue) => {
          setValue(newValue);
        }}
        onChangeActive={(event, newHover) => {
          setHover(newHover);
        }}
        emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
      />
      {value !== null && (
        <Box sx={{ ml: 2 }}>{labels[hover !== -1 ? hover : value]}</Box>
      )}
    </Box>
     <Box component="form" onSubmit={(e)=>{if(imaPravo){handleSubmit(e)}
    else{e.preventDefault();}}} sx={{display:"flex" ,alignItems:"center",gap:"20px"}} >
      <TextField value={komentar} onChange={(e)=>{setKomentar(e.target.value)}}
      sx={{width:"40%"}}required placeholder='Podijelite svoje mišljenje s nama'></TextField>
    <Button onClick={()=>
    {if(korisnikovaRecenzija.ocjena!==null){
      alert("Već ste ostavili svoju recenziju.")
      setImaPravo(false);
     return false;
    }else if (email===null){
      
      setImaPravo(false);
    
      alert("Ne možete ostaviti komentar ako niste prijavljeni na stranicu")
    }
    }} type="submit" variant="contained" color="primary">Spremi</Button>
    </Box>
   
    <Box style={{paddingTop:"2%", paddingBottom:"2%"}}>
      {random.length===0&&
          (
              <Box >
                 <Typography variant="h6"  sx={{color:'#0059B2'}}>
     Nemamo još recenzija
     </Typography>
              </Box>
          )  

      }
      { random.length!==0  && (
        <Box sx={{paddingTop:"0.5%",display:"flex",justifyContent:"space-around",flexDirection:"column",gap:"15px"}}>
        <Box sx={{display:"flex", flexDirection:"row",gap:"2%"}}><Typography>Prosječna ocjena</Typography><Rating value={average} precision={0.5} readOnly/></Box>
       <Typography >Ovo su drugi rekli o nama</Typography> 
       </Box>)}
      { random.length!==0  &&
       
        random.map((val,ind)=>
          (
              <Box key={ind} sx={{border: '1.5px solid #ddd',borderRadius:"10px"}} >
                <Box><Rating value={val.ocjena} precision={0.5} readOnly/></Box>
                <Box>{val.komentar}</Box>
              </Box>
          )
      )
      }
    </Box>
    </Box>
  );
}

