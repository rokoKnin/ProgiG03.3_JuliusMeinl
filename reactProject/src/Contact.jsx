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
      <APIProvider apiKey={import.meta.env.VITE_GOOGLE_MAPS_API_KEY}>
        <CustomMap />
      </APIProvider>
    </div>
    </main>
  );
}

export default Contact;
