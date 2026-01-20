import { Typography } from '@mui/material';
import { Link,useNavigate } from 'react-router-dom';
import CustomMap from "../CustomMap";
import { APIProvider } from "@vis.gl/react-google-maps";
import * as React from 'react';
export default function Footer(){
  const navigate=useNavigate();
  const email = localStorage.getItem('email');
  const handleLoginClick = (vari) => {
    if(!email){
        window.location.href = `${import.meta.env.VITE_API_URL}` + '/oauth2/authorization/google';
    }else{
      navigate(vari);
    }
      };
    return(
        <div className='footerBody'>
            <div className='footerContent'>
                <div >
                    <h3>Kontakt</h3>
                    <div>
                        <p>Adresa: Izmišljenaadr 5, Moregrad</p>
                        <p>Telefon: 011 244 5xx</p>
                        </div></div>
                <div style={{display:'flex',width:'10%',height:'200px',flexDirection:'column', gap: '0.5rem',alignItems:"center",justifyContent:"center"}}><h3>Lokacija</h3>
                       <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2606.0291400333235!2d14.848037724060847!3d45.08741236591118!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x476383f9a2c5bd55%3A0x96a63851a0accce6!2sSmokvica%20pla%C5%BEa!5e1!3m2!1shr!2shr!4v1768928955816!5m2!1shr!2shr"
      style={{ width:"150%" ,height:"auto", style:"border:0;", allowfullscreen:"", loading:"lazy", referrerpolicy:"no-referrer-when-downgrade"}}></iframe>
   </div>
                <div style={{display:'flex',flexDirection:'column', gap: '0.5rem'}}><h3>Ostalo</h3>
                
                    <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/about">
              O nama
            </Link>
            
                    <Typography onClick={()=>handleLoginClick("/reservation")} style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}}>
              Rezervacija soba
            </Typography>
                    <Typography onClick={()=>handleLoginClick("/reservationAdditionalServices")}style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} >
              Rezervacija dodatnog sadržaja
            </Typography>
                    <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/gallery">
              Galerija
            </Link>
             <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/reviews">
              Recenzije
            </Link>
            <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/faq">
              FAQ
            </Link></div>
                </div>
            </div>
    );
}

