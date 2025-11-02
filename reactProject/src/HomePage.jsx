
import * as React from 'react';
import './index.css';

import { Link } from 'react-router-dom';

function HomePage() {
  

  return (
    <main style={{ padding: 16 }}>
      <h2>Dobrodošli u Blue sun hotel</h2>
    <Link to="/reservation" style={{ textDecoration: 'none' }}>
    <div>Rezervirajte odmah!</div>
</Link>
    </main>
    
  );
}

export default HomePage;