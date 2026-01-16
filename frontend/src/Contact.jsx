import * as React from 'react';
import './index.css';
function Contact() {
  return (
    <main style={{ padding: 16 }}>
      <h2>kontaktirajte nas</h2>
      <p>ili posjetite na ovoj lokaciji:</p>
      <div className="mapa">
      <iframe src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d1085894.2553766766!2d13.023369162805468!3d45.803510891287566!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x477c9d559c79b335%3A0x7c4f372e65106a7d!2sbeach%20%C5%A0pina!5e0!3m2!1shr!2shr!4v1768478794101!5m2!1shr!2shr"
      style={{width:"75%", height:"auto"}} allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
    </div>
    </main>
  );
}

export default Contact;
