import * as React from 'react';
import './index.css';
import { Box} from '@mui/material';
function Gallery() {
  let i=[...Array(10)];
  return (
    <Box sx={{ 
      display: "flex", 
      flexDirection: "coolumn", 
      flexWrap: "wrap", 
      justifyContent: "center",
      alignItems: "center",
      gap: 2, 
      padding: 2
    }}>
    {i.map((v,i)=>(
      ( <Box component="img" key={i} src={`./slika_${i+1}.jpg`} style={{width:"50%",height:"250px", borderRadius:"5px",objectFit: 'cover'}}></Box>)
    ))}
    </Box>
  );
}

export default Gallery;