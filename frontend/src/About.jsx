
import * as React from 'react';
import { Box,Container,Typography } from '@mui/material';
export default function About(){
    return(
       
                <Box sx={{backgroundColor:"#bbb",borderRadius:"10px",padding:"2%"}}>
                    <Typography variant="h4" sx={{ color: '#005b96', fontWeight: 'bold', mb: 2 }}>
                        Naša Priča
                    </Typography>
                    
                    <Typography variant="body1" sx={{ color: '#333', lineHeight: 1.8, mb: 4, fontSize: '1.1rem' }}>
                        Iza svakog uspješnog hotela stoji tim ljudi koji vole ono što rade. Mi smo obitelj
                        koja već godinama s ponosom dočekuje goste iz cijeloga svijeta. 
                        Za nas, vi niste samo broj rezervacije – vi ste naši gosti kojima želimo pružiti osjećaj drugog doma.
                    </Typography>
                    <Typography variant="body1" sx={{ color: '#333', lineHeight: 1.8, fontSize: '1.1rem' }}>
                        Vjerujemo da luksuz nije samo u materijalnim stvarima, već u trenucima mira. 
                        Naš hotel je osmišljen kao utočište od užurbanosti, mjesto gdje miris soli 
                        i šum valova postaju vaša svakodnevica. Naš cilj je pružiti vam autentično 
                        iskustvo Mediterana uz vrhunsku uslugu koju zaslužujete.
                    </Typography>
                </Box>
    
)}