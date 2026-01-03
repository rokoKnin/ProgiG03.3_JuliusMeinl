import { Button } from '@mui/material';
import { Link } from 'react-router-dom';
import CustomMap from "../CustomMap";
import { APIProvider } from "@vis.gl/react-google-maps";
import * as React from 'react';
export default function Footer(){
    return(
        <div className='footerBody'>
            <div className='footerContent'>
                <div >
                    <h3>Kontakt</h3>
                    <div>
                        <p>Posjetite nas na adresi: Izmišljenaadr 5, Moregrad</p>
                        <p>Telefon: 011 244 5xx</p>
                        </div></div>
                <div style={{display:'flex',width:'10%',height:'200px',flexDirection:'column', gap: '0.5rem'}}><h3>Lokacija</h3><APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY}>
                        <CustomMap />
                      </APIProvider></div>
                <div style={{display:'flex',flexDirection:'column', gap: '0.5rem'}}><h3>Ostalo</h3>
                
                    <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/about">
              O nama
            </Link>
                    <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/reservation">
              Rezervacija soba
            </Link>
                    <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/reservationAdditionalServices">
              Rezervacija dodatnog sadržaja
            </Link>
                    <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/gallery">
              Galerija
            </Link></div>
                </div>
            </div>
    );
}

