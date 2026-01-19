import React from "react"
import {
  AppBar,
  Button,
  Box,
  Toolbar,
  IconButton,
  Typography,
  Menu,
  MenuItem,
} from '@mui/material';
import "../index.css"
import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';

function Header() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  //const [email, setEmail] = React.useState(localStorage.getItem("email"));
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
    const handleLoginClick = () => {
        window.location.href = `${import.meta.env.VITE_API_URL}` + '/oauth2/authorization/google';
    };
    //const email = localStorage.getItem("email");

    //  console.log(window.location.hash)
    const hash = window.location.hash;
    const queryString = hash.split("?")[1];
    const params = new URLSearchParams(queryString);

    let ovlast =localStorage.getItem("ovlast");
    if (!ovlast) {
      ovlast = params.get("ovlast");
      if (ovlast) {
        localStorage.setItem("ovlast", ovlast);
      }
    }
    let email =localStorage.getItem("email");
    if (!email) {
      email = params.get("email");
      if (email) {
        localStorage.setItem("email", email);
      }
    }

    // console.log("ovo je mail")
    // console.log(email)

  // useEffect(() => {
  //       axios.get(`${import.meta.env.VITE_API_URL}` + '/api/users/info', {withCredentials: true}).then(response =>
  //       { setUser(response.data);
  //           console.log(user);
  //       })
  //           .catch(error => console.error('Error ocurred', error))
  //   }, []);

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
            aria-controls={open ? 'basic-menu' : undefined}
            aria-haspopup="true"
            aria-expanded={open ? 'true' : undefined}
            onClick={handleClick}
          >
            <MenuIcon />
          </IconButton>
        
          <Menu

            id="basic-menu"
            anchorEl={anchorEl}
            open={open}
            onClose={handleClose}
            slotProps={{
              list: {
                'aria-labelledby': 'basic-button',
              },
            }}
          >
            <MenuItem onClick={handleClose} component={Link} to="/">
              Početna stranica
            </MenuItem>
            <MenuItem onClick={handleClose} component={Link} to="/about">
              O nama
            </MenuItem>
            <MenuItem onClick={handleClose} component={Link} to="/reservation">
              Rezervacija soba
            </MenuItem>
            <MenuItem onClick={handleClose} component={Link} to="/reservationAdditionalServices">
              Rezervacija dodatnog sadržaja
            </MenuItem>
             <MenuItem onClick={handleClose} component={Link} to="/reviews">
              Recenzije
            </MenuItem>
            <MenuItem onClick={handleClose} component={Link} to="/gallery">
              Galerija
            </MenuItem>
            <MenuItem onClick={handleClose} component={Link} to="/faq">
              FAQ
            </MenuItem>
            <MenuItem onClick={handleClose} component={Link} to="/contact">
              Kontakt
            </MenuItem>
          </Menu>
          <Typography variant="h6" className="logo" component={Link} to="/" sx={{
                            flexGrow: 1, 
                            textDecoration: 'none', 
                            color: 'white', 
                            cursor: 'pointer'
                        }}>
            Modrila
          </Typography>
          
          {ovlast && (
  <div>

    {ovlast === "VLASNIK" && (
      <Button color="inherit" component={Link} to="/adminStart">
            Admin
          </Button>
    )}
  </div>
        )}{ovlast && (
  <div>

    {ovlast && (
      <Button color="inherit" component={Link} to="/profil">
            profil
          </Button>
    )}
  </div>
        )} {!ovlast && (
  
  <div>

    
      <Button color="inherit" onClick={handleLoginClick}>
            Login
          </Button>
    
  </div>
        )}               
          
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default Header;
