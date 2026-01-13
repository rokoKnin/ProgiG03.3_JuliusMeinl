import {
  TextField,
  Button,
  Box,
  
 
} from '@mui/material';
import axios from 'axios' ;
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
function Payment() {
    const navigate=useNavigate();
    const handleSubmit=async(event)=>{
        event.preventDefault();
        const uspjeh=await postpay();
        if(uspjeh){navigate('/profil')}
    }
    const location=useLocation();
    const sadrzaj=location.state;
    async function postpay(){
      console.log(sadrzaj);
      try {
                   const response= await axios.post(`${import.meta.env.VITE_API_URL}` + '/api/reservations', sadrzaj,  {withCredentials: true} )
                  
                   return true;
                
                } catch (error) {
                  
                  console.log(sadrzaj)
                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
                   console.log(error.response.headers)
                   console.log(error.response.status)
                    return false;
                  }
            
    }
    return(
        <Box component="form" onSubmit={handleSubmit}>
        <Box sx={{display:"flex",flexDirection:"row",gap:"20px"}}>
            <TextField
          required
          id="brojKartice"
          label="Broj kartice"
          variant="outlined"
        /><TextField
          required
          id="cvv"
          label="CVV"
          variant="outlined"
        /><TextField
          required
          id="datumIsteka"
          label="Datum isteka"
          variant="outlined"
        />
        </Box>
        <Box>
            
         <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="outlined" onClick={()=>{}}component={Link} to="/home">Odustani</Button>
         <Button type="submit" sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="contained">Spremi</Button>
        </Box>
        </Box>
    );
}

export default Payment;