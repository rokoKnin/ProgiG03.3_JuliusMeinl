import * as React from 'react';
import './index.css';
import CustomMap from "./CustomMap";
import { APIProvider } from "@vis.gl/react-google-maps";
function Contact() {
  return (
    <main style={{ padding: 16 }}>
      <h2>kontaktirajte nas</h2>
      <p>ili posjetite na ovoj lokaciji:</p>
      <div className="mapa">
      <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2606.0291400333235!2d14.848037724060847!3d45.08741236591118!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x476383f9a2c5bd55%3A0x96a63851a0accce6!2sSmokvica%20pla%C5%BEa!5e1!3m2!1shr!2shr!4v1768928955816!5m2!1shr!2shr"
      style={{ width:"600px" ,height:"450px",  allowfullscreen:"", loading:"lazy", referrerpolicy:"no-referrer-when-downgrade"}}></iframe>
    </div>
    </main>
  );
}

export default Contact;
