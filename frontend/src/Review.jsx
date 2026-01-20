import Box from '@mui/material/Box';
import Rating from '@mui/material/Rating';
import { TextField } from '@mui/material';
import StarIcon from '@mui/icons-material/Star';
import React, {useState} from "react"
import {Button} from '@mui/material';
import axios from "axios";
import { Link, useNavigate } from 'react-router-dom';
const labels = {
  1: 'Nezadovoljavajuće',
  1.5: 'Nezadovoljavajuće prema zadovoljavajućem',
  2: 'Zadovoljavajuće',
  2.5: 'Zadovoljavajuće prema dobrom',
  3: 'Dobro',
  3.5: 'Dobro prema vrlo dobrom',
  4: 'Vrlo dobro',
  4.5: 'Vrlo dobro prema odličnom',
  5: 'Odlično',
};
function getLabelText(value) {
    
  return `${value} Star${value !== 1 ? 's' : ''}, ${labels[value]}`;
}
export default function Review() {
    const navigate = useNavigate();
   const [value, setValue] = React.useState(2);
  const [hover, setHover] = React.useState(-1);
    const [komentar,setKomentar]=React.useState("");
    const email = localStorage.getItem('email')
    const handleSubmit=async(event)=>{
        event.preventDefault();
        const data={
            value,
            komentar,
            email
        }
         try {
                   const response= await axios.post(`${import.meta.env.VITE_API_URL}`+'/api/reviews/' + `${email}`, data,  {withCredentials: true} )
                   navigate("/").then(window.location.reload)
                   return true;

                } catch (error) {

                    console.error('Error: nije se poslao post zbog necega', error.response?.data)

                    return false;
                  }
    }
  return (
    <Box sx={{  display: 'flex',flexDirection:"column", }}>
        <Box >
      <Rating
        name="hover-feedback"
        value={value}
        precision={0.5}
       getLabelText={getLabelText}
        onChange={(event, newValue) => {
          setValue(newValue);
        }}
        onChangeActive={(event, newHover) => {
          setHover(newHover);
        }}
        emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
      />
      {value !== null && (
        <Box sx={{ ml: 2 }}>{labels[hover !== -1 ? hover : value]}</Box>
      )}
    </Box>
     <Box component="form" onSubmit={handleSubmit} sx={{display:"flex" ,alignItems:"center",gap:"20px"}} >
      <TextField value={komentar} onChange={(e)=>{setKomentar(e.target.value)}}
      sx={{width:"40%"}}required placeholder='Podijelite svoje mišljenje s nama'></TextField>
    <Button type="submit" variant="contained" color="primary">Spremi</Button>
    </Box>
    </Box>
  );
}

