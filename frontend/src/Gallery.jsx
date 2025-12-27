import * as React from 'react';
import './index.css';
import { Typography} from '@mui/material';
import { Link } from 'react-router-dom';
function Gallery() {
  

  return (
    <div  className="galleryBody">
        <div>
      <h2 style={{ display:'flex', justifyContent:'center', alignItems:'center', color: '#4e5256ff', backgroundColor: '#8dbbeaff', width: '20%', height: '10%', borderRadius: '10px' }}>Dvokrevetne sobe</h2>
        </div>
        <div>
      <h2 style={{ display:'flex', justifyContent:'center', alignItems:'center', color: '#4e5256ff', backgroundColor: '#8dbbeaff', width: '20%', height: '10%', borderRadius: '10px' }}>Trokrevetne sobe</h2>
        </div>
        <div>
      <h2 style={{ display:'flex', justifyContent:'center', alignItems:'center', color: '#4e5256ff', backgroundColor: '#8dbbeaff', width: '20%', height: '10%', borderRadius: '10px' }}>Penthouse</h2>
        </div>
        </div>
    
  );
}

export default Gallery;