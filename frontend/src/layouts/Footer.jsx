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
                        </div>
                        </div>
                <div style={{display:'flex',width:'30%',height:'auto',flexDirection:'column', gap: '0.5rem',alignItems:"center"}}><h3>Lokacija</h3>
                <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1085894.2553766766!2d13.023369162805468!3d45.803510891287566!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x477c9d559c79b335%3A0x7c4f372e65106a7d!2sbeach%20%C5%A0pina!5e0!3m2!1shr!2shr!4v1768478794101!5m2!1shr!2shr"
      style={{width:"75%", height:"auto"}} allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
    
                      </div>
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
            </Link>
             <Link style={{all:'unset',color: 'white', size: 'small', cursor: 'pointer'}} to="/reviews">
              Recenzije
            </Link></div>
                </div>
            </div>
    );
}

