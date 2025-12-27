
import * as React from 'react';
import './index.css';
import { Typography} from '@mui/material';
import { Link } from 'react-router-dom';

function HomePage() {
  

  return (
    <main style={{ padding: 16 }}>
      <h2 style={{ color: '#1976d2' }}>Dobrodošli u Modrila hotel</h2>
      <div className="home_container">
     <Typography variant="h6" component={Link} to="/reservation"className="link_reservation">
     Rezervirajte odmah!
     </Typography>
     <img src="https://images.unsplash.com/photo-1618773928121-c32242e63f39?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aG90ZWx8ZW58MHx8MHx8fDA%3D&auto=format&fit=crop&q=60&w=600"></img>
             </div>
    </main>
    
  );
}

export default HomePage;