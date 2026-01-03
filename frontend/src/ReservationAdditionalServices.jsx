import {Box,Button,Typography} from '@mui/material'
import React, { createElement, useState } from 'react';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import { createPortal } from 'react-dom';

function Modal({ isOpen, onClose, children }) {
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
         <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="outlined" onClick={onClose}>Odustani</Button>
         <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="contained" onClick={()=>{}}>Spremi</Button>
      </div>
    </div>,
    document.body
  );
}
export default function ReservationAdditionalServices({showNext}) {
    
    const[datum,setDatum]=React.useState(dayjs().startOf('day'));
    const[datumDanas,setDatumDanas]=React.useState(dayjs().startOf('day'));
    const [isOpen, setIsOpen] = useState({open:false,name:null});
    const {open,name}=isOpen;
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
    {showNext&&(<Box style={{display:"flex", justifyContent:"flex-end"}}><Button variant="contained">Next</Button></Box>)
    }
    
    <Modal isOpen={isOpen} onClose={() => setIsOpen({open:false,name:null})}>
    <LocalizationProvider dateAdapter={AdapterDayjs}>
          <DemoContainer components={['DatePicker', 'DatePicker']}>
            <Typography>{name}</Typography>
            <DatePicker label="Datum" name="datum" minDate={datumDanas}value={datum} onChange={(newValue) => setDatum(newValue)} format="DD.MM.YYYY" />
            </DemoContainer>
        </LocalizationProvider>
    </Modal>
    </div>)
}