import {Box,Button,Typography} from '@mui/material'
import React, { createElement, useState, useEffect } from 'react';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { createPortal } from 'react-dom';

import axios from "axios";

function Modal({ isOpen, onClose, children,onConfirm,onOdustani }) {
  if (!isOpen.open) return null;

  return createPortal(
    <div style={{
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(0, 0, 0, 0.5)',
      display: 'flex',
      alignItems: 'center',
      justifyContent: 'center'
    }}>
      <div style={{
        background: 'white',
        padding: '20px',
        borderRadius: '8px'
      }}>
        {children}
         <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="outlined" onClick={()=>{onOdustani();onClose();}}>Odustani</Button>
         <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="contained" onClick={()=>{onConfirm(); onClose();}}>Spremi</Button>
      </div>
    </div>,
    document.body
  );
}

export default function ReservationAdditionalServices({showNext,onUpdate}) {
    
    const[datum,setDatum]=React.useState(dayjs().startOf('day'));
    const[datumDanas,setDatumDanas]=React.useState(dayjs().startOf('day'));
    const [isOpen, setIsOpen] = useState({open:false,name:null});
    const {open,name}=isOpen;
    const [lista,setLista]=React.useState([])
    const [user,setUser]=React.useState();
    const[dodatniSadrzaj,SetdodatniSadrzaj]=React.useState([[],[],[]]);
    const [odabraneSobe,setOdabraneSobe]=React.useState([]);

  async function postDodatniSadrzaj(odabraneSobe,odabraniDodatniSadrzaj){
      const sadrzaj={
        odabraneSobe,
        odabraniDodatniSadrzaj
      }
      try {
                   return await axios.post(`${import.meta.env.VITE_API_URL}` + '/api/reservations', sadrzaj,  {withCredentials: true} )
                  
                
                } catch (error) {
                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
                   
                    return false;
                  }
            
    }

    const handleAdd=()=>{
     
      const rez= {
        vrstaDodatnogSadržaja: name,
        datum: datum
      }
      let index=0;
      if(name==="Bazen"){
        index=0;
      }
      else if (name==="Restoran"){
        index=1;
      }
      else{
       index=2;
      }
      const postoji=dodatniSadrzaj[index].some((obj)=>obj.datum.isSame(datum,'day'))
    
      if(!postoji){
      SetdodatniSadrzaj((prev) => {
        return prev.map((kategorija, i) => {
            if (i === index) {
                return [...kategorija, rez];
            }
            return kategorija;
        });
    });
    }   
    }
    function handleDelete(ind,datum){
      SetdodatniSadrzaj((prev)=>{
        return prev.map((kat,i)=> {if(i===ind){
          return kat.filter(objekt=>!objekt.datum.isSame(datum,'day'))
      }
    return kat;
    })
      })
    }
    
    const slanje=()=>{
      if(!showNext){
       if(onUpdate){
        
        onUpdate(dodatniSadrzaj);
       } }
    }
    const handleNextAlone=async()=>{
      const formatirano=dodatniSadrzaj.map((prev)=>
        prev.map((kat,i)=>({
            ...kat,datum:kat.datum.format('YYYY-MM-DD')
          }))
        );
        const uspjeh=await postDodatniSadrzaj(odabraneSobe,formatirano);
        
      }
    const handleOdustani=(name)=>{
       let index=0;
      if(name==="Bazen"){
        index=0;
      }
      else if (name==="Restoran"){
        index=1;
      }
      else{
       index=2;
      }
      
      SetdodatniSadrzaj((prev)=> {return prev.map((kat,i)=>{
        if(i===index){
          return [];
        }
        return kat;
      })});
      if(!showNext){
      slanje();}
    }
     useEffect(() => {
            axios.get(`${import.meta.env.VITE_API_URL}` + '/api/users/info', {withCredentials: true}).then(response =>
            { setUser(response.data);
            })
                .catch(error => console.error('Error ocurred', error))
        }, []);
      let ind=0;
      if(name==="Bazen"){
        ind=0;
      }
      else if (name==="Restoran"){
        ind=1;
      }
      else{
       ind=2;
      }
    return(
        <div style={{display:'flex',flexDirection:"column",justifyContent:"space-around",gap:"2rem"}}>
        <div style={{display:"flex", flexDirection:"row", justifyContent:"space-around"}}>
    <Box component="button" sx={{display:"flex",flexDirection:"column",justifyContent:"space-around",alignItems:"center",width:"30%",minheight:"350px", padding:"2rem",backgroundColor:'#66b2ff',border: '2px solid #e0e0e0','&:hover': {
            borderColor:"#007fff"},
          borderRadius: '12px'}} onClick={() => setIsOpen({open:true,name:"Bazen"})}>
        <img src="./pool2.jpg" style={{width:"50%", borderRadius:"5px"}}></img>
        <Typography sx={{color:"#e0e0e0"}}>Bazen</Typography>
    </Box>
    <Box component="button" sx={{display:"flex",flexDirection:"column",justifyContent:"space-around",alignItems:"center",width:"30%",minheight:"350px", padding:"2rem",backgroundColor:'#66b2ff',border: '2px solid #e0e0e0','&:hover': {
            borderColor:"#007fff"},
          borderRadius: '12px'}}onClick={() => setIsOpen({open:true,name:"Restoran"})}>
        <img src="./restaurant.jpg" style={{width:"50%",borderRadius:"5px"}}></img>
        <Typography sx={{color:"#e0e0e0"}}>Restoran</Typography>
    </Box>
    <Box component="button" sx={{display:"flex",flexDirection:"column",justifyContent:"space-around",alignItems:"center",width:"30%", minheight:"350px", padding:"2rem",backgroundColor:'#66b2ff',border: '2px solid #e0e0e0','&:hover': {
            borderColor:"#007fff"},
          borderRadius: '12px'}} onClick={() => setIsOpen({open:true,name:"Teretana"})}>
        <img src="./gym.jpg" style={{width:"50%",borderRadius:"5px",}}></img>
        <Typography sx={{color:"#e0e0e0"}}>Teretana</Typography>
    </Box>
    </div>
    {showNext&&(<Box style={{display:"flex", justifyContent:"flex-end"}}>
      <Button variant="contained" onClick={handleNextAlone}>Next</Button></Box>)
    }
    
    <Modal isOpen={isOpen} onConfirm={slanje} onOdustani={()=>{handleOdustani(isOpen.name)}} onClose={() => setIsOpen({open:false,name:null})}>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <Box sx={{width:"100%",display:"flex",flexDirection:"column", gap:"1rem"}}>
            
              <Typography variant="h5"color="primary">{name}</Typography>
              <Box sx={{width:"100%",display:"flex",flexDirection:"row", justifyContent:"space-around"}}>
            <DatePicker sx={{width:"70%"}}label="Datum" name="datum" minDate={datumDanas}value={datum} onChange={(newValue) => setDatum(newValue)} format="DD.MM.YYYY" />
            <Button variant="contained" color="primary" onClick={handleAdd}>+</Button>
             </Box>
             
             
          
                {name && dodatniSadrzaj.at(ind).map((kategorija,index)=>(
                  
                 <Box>
                  <Box sx={{display:"flex",justifyContent:"space-around",alignItems:"center"}}>{kategorija.datum.format("DD.MM.YYYY")}
                    <Button sx={{color:"white",backgroundColor:"red",borderRadius:"15px"} }onClick={()=>handleDelete(ind,kategorija.datum)}>x</Button></Box>
                   </Box>
                   )
)}
            
            </Box>
        </LocalizationProvider>
    </Modal>
    </div>)
}