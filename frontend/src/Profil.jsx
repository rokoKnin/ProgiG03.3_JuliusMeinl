import React, { useEffect } from 'react';
import {Box, Button, colors, TextField,Typography} from '@mui/material';
import axios from 'axios' ;
import Rating from '@mui/material/Rating';
import StarIcon from '@mui/icons-material/Star';
import { createPortal } from 'react-dom';
import dayjs from 'dayjs';
import { useNavigate } from 'react-router-dom';

function Modal({ isOpen, onClose, children }) {
    if (!isOpen) return null;

    return createPortal(
        <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
        }}>
            <div style={{
                background: 'white',
                padding: '20px',
                borderRadius: '8px'
            }}>
                {children}

                </div>
        </div>,
        document.body
    );
}

function ModalRezervacija({ isOpenRez, onClose, children}) {
    if (!isOpenRez) return null;

    return createPortal(
        <div style={{
            position: 'fixed',
            top: 0,
            left: 0,
            right: 0,
            bottom: 0,
            backgroundColor: 'rgba(0, 0, 0, 0.5)',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center'
        }}>
            <div style={{
                background: 'white',
                padding: '20px',
                borderRadius: '8px'
            }}>
                {children}

                </div>
        </div>,
        document.body
    );
}

function Profil() {
    const [email,setEmail]=React.useState(localStorage.getItem("email"));
    const [user,setUser]=React.useState("");
    const [data,setdata]=React.useState([]);
    const [korisnikovaRecenzija,setKorisnikovaRecenzija]=React.useState(null);
    const [isOpen,setIsOpen]=React.useState(false);
    const [isOpenRez,setIsOpenRez]=React.useState(false);
    const [editUser,setEditUser]=React.useState({})
    const [refresh,setRefresh]=React.useState(0);
    const [odabranaRez,setOdabranaRez]=React.useState(null);
    const [lista,setLista]=React.useState([]);
    const navigate=useNavigate();
     useEffect(()=>{
     const listaDrz=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/profile/countries-list', {withCredentials: true})
             .then(listaDrz =>
              { 
               setLista(listaDrz.data);
                 //console.log("drzave",listaDrz.data);
              })
                  .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
                 console.error(error.response?.status)
                 console.error(error.response)})
          }, []);
     useEffect(()=>{
      
        const korisnikRec=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/reviews/'+`${email}`, {withCredentials: true})
        .then(korisnikRec =>
         { 
          setKorisnikovaRecenzija(korisnikRec.data);
           // console.log(korisnikRec.data);
         })
             .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)})
     }, []);
     
     useEffect(()=>{
      
        const before=axios.get(`${import.meta.env.VITE_API_URL}`+'/api/profile/reservations/' + `${email}`, {withCredentials: true})
        .then(before =>
         { 
          setdata(before.data);
            //console.log(before.data);
         })
             .catch(error =>{console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)})
     }, []); 
   
     async function postUser(){
      try {
                   const response= await axios.post(`${import.meta.env.VITE_API_URL}`+'/api/profile/' + `${email}`, email,  {withCredentials: true} )
                  setUser(response.data);
                  console.log(response.data);
                   return true;
                
                } catch (error) {
                  
                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
                   console.log(error.response.headers)
                   console.log(error.response.status)
                    return false;
                  }
            
    }
    useEffect(()=>{
    if(email){
    postUser();
    
}},
[refresh])

const handleChange = (e) => {
    const { name, value } = e.target;
    console.log(`Promjena na polju ${name}:`, value);
    setEditUser({ ...editUser, [e.target.name]: e.target.value });
    console.log(editUser)
};
const handleSave=async()=>{
          try {
                   const response= await axios.put(`${import.meta.env.VITE_API_URL}`+'/api/profile/edit' , editUser,  {withCredentials: true} )
                   setRefresh(prev=>prev+1)
                   
                   console.log("editUser promijenjen:", editUser);
                   return true;
                
                } catch (error) {
                  
                    console.error('Error: nije se poslao post zbog necega', error.response?.data)
                  
                   console.log(error.response.status)
                    return false;
                  }
            
    }
    const openModalRez=(rez)=>{
        
    setOdabranaRez(rez);
    setIsOpenRez(true);
    }
    const prikazSoba={
        "DVOKREVETNA_KING":{label:"Dvokrevetna king soba"},
        "TROKREVETNA":{label:"Trokrevetna soba"},
        "DVOKREVETNA_TWIN":{label:"Dvokrevetna twin soba"},
        "PENTHOUSE":{label:"Penthouse"},
        "Balkon":{label:"Balkon"},
        "Pogled na more":{label:"Pogled na more"}

    }
    const handleLogout=()=>{
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = '/';
    }
    return(
        <Box >
            <Box 
            sx={{display: "flex",justifyContent: "space-between",alignItems: "center",
            padding: "2%",backgroundColor: "#66b2ff",borderRadius: "15px",gap: 3 
        }}
        >
        <Box sx={{ display: "flex", flexDirection: "column", gap: 1, flex: 1 }}>
            <Box>Ime: {user.name}</Box>
            <Box>Broj telefona: {user.telefon}</Box>
            <Box>Grad: {user.grad}</Box>
            <Box>Poštanski broj: {user.postBr}</Box>
        </Box>
        <Box sx={{ display: "flex", flexDirection: "column", gap: 1, flex: 1 }}>
            <Box>Prezime: {user.prezime}</Box>
            <Box>Email: {user.email}</Box>
            <Box>Država: {user.drzava}</Box>
        </Box>
        <Box sx={{ display: "flex", flexDirection:"column",justifyContent: "center", minWidth: "100px" }}>
            <Button variant='contained' color="primary" onClick={()=>{setIsOpen(true); setEditUser(user)}}>
            Uredi
            </Button>
            <Button variant='contained' color="error" onClick={()=>handleLogout()}>
            Log out
            </Button>
        </Box>
        </Box>
            <Box>
  {korisnikovaRecenzija && (
    <Box>
      <Typography sx={{ paddingTop: "2%" }}>Ovo ste vi rekli o nama</Typography>
      <Box>
        <Box>
          <Rating value={Number(korisnikovaRecenzija.ocjena)} precision={0.5} readOnly />
        </Box>
        <Box>{korisnikovaRecenzija.komentar}</Box>
      </Box>
    </Box>
  )}
</Box>
<Box sx={{padding:"2%"}}>
    {
        data.map((val,ind)=>(
            <Box sx={{border: '1px solid #ddd',borderRadius:"15px", padding:"2%",display:"flex",flexDirection:"row",alignItems:"center",justifyContent:"space-around"}}>
            <Box sx={{display:"flex",justifyContent:"center",flexDirection:"column"}}>
               <Typography ><span>Datum rezerviranja </span>{dayjs(val.datumRezerviranja).format("DD.MM.YYYY")}</Typography>
               <Typography ><span>Cijena </span>{val.iznosRezervacije}<span> eura</span></Typography>
            </Box><Button onClick={()=>{openModalRez(val)}} sx={{color:"#e7e6e6"}} >Pogledajte više o rezervaciji</Button>
            </Box>
        ))
    }
</Box>
{isOpenRez && odabranaRez && (
  <ModalRezervacija isOpenRez={isOpenRez} onClose={() => setIsOpenRez(false)}>
    <Box sx={{ 
    maxHeight: '80vh',overflowY: 'auto',width: '100%',
    maxWidth: '500px',  paddingRight: '8px', '&::-webkit-scrollbar': { width: '8px' }
  }}>
    {odabranaRez.sobe?.map((odabran, ind) => (
      
        <Box key={ind} sx={{border: '2px solid #66a4e1',padding:"2%"}}>
        <Typography>
          Datum dolaska {dayjs(odabran.datumOd).format("DD.MM.YYYY")}
        </Typography>
        <Typography>
          Datum odlaska {dayjs(odabran.datumDo).format("DD.MM.YYYY")}
        </Typography>
        <Box sx={{border: '2px solid #99c6f4',padding:"2%"}}>
        <Typography sx={{display:"flex",flexDirection:"row", gap:"5px"}}>{prikazSoba[odabran.soba.vrsta].label}</Typography>
        {odabran.soba.balkon && <Typography sx={{display:"flex",flexDirection:"row", gap:"5px"}}>{prikazSoba["Balkon"].label}</Typography>}
        {odabran.soba.pogledNaMore && <Typography sx={{display:"flex",flexDirection:"row", gap:"5px"}}>{prikazSoba["Pogled na more"].label}</Typography>}
          </Box>    
    </Box>
    ))}                    
    <Box sx={{border: '2px solid #9be9b4',padding:"2%"}}>
     {odabranaRez.sadrzaji?.map((odabran, ind) => (
        <Box key={ind}sx={{padding:"2%"}}>
            <Typography>{odabran.dodatniSadrzaj.vrsta}</Typography>
        </Box>        
     ))}
    </Box>
      </Box>
    

    <Button onClick={() => setIsOpenRez(false)}>Izađi iz pregleda</Button>
  </ModalRezervacija>
)}

<Modal isOpen={isOpen} onClose={() => setIsOpen(false)}>
    <Box sx={{display: "flex",justifyContent: "space-between",alignItems: "center",
            padding: "2%",borderRadius: "15px",gap: 3 
        }}>
    <Box sx={{ display: "flex", flexDirection: "column", gap: 1, flex: 1 }}>
            <Box sx={{ display: "flex",flexDirection:"row", gap :"2%", alignItems:"center"}}>Ime: <TextField name="name" onChange={handleChange} defaultValue={user.name}/></Box>
            <Box sx={{ display: "flex",flexDirection:"row", gap :"2%", alignItems:"center"}}>Broj telefona:  <TextField type="number" name="telefon" onChange={handleChange} defaultValue={user.telefon}/></Box>
            <Box sx={{ display: "flex",flexDirection:"row", gap :"2%", alignItems:"center"}}>Grad:  <TextField name="grad" onChange={handleChange} defaultValue={user.grad}/></Box>
            <Box sx={{ display: "flex",flexDirection:"row", gap :"2%", alignItems:"center"}}><TextField name="postBr" onChange={handleChange} defaultValue={user.postBr}/></Box>
        </Box>
        <Box sx={{ display: "flex", flexDirection: "column", gap: 1, flex: 1 }}>
            <Box sx={{ display: "flex",flexDirection:"row", gap :"2%", alignItems:"center"}}>Prezime:  <TextField name="prezime" onChange={handleChange} defaultValue={user.prezime}/></Box>
            <Box sx={{ display: "flex",flexDirection:"row", gap :"2%", alignItems:"center"}}>Email:  <TextField disabled defaultValue={user.email}/></Box>
           <Box> <select name="drzava" onChange={handleChange} 
            value={editUser.drzava || ""}
            style={{ 
                padding: '12px', 
                borderRadius: '4px', 
                border: '1px solid #ccc',
                width: '100%',
                marginTop: '5px' 
            }}
            >
            <option value="" disabled>Odaberi državu</option>
            
            {lista.map((drzava) => (
                <option key={drzava} value={drzava}>
                {drzava}
                </option>
            ))}
            </select>   </Box>
            </Box>
        </Box>
        <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="outlined" onClick={()=>{setIsOpen(false)}}>Odustani</Button>
        <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="contained" onClick={()=>{handleSave();setIsOpen(false);}}>Spremi</Button>
            
</Modal>
</Box>
         
         
    );
}

export default Profil;