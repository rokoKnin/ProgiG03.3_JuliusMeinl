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
  const email = localStorage.getItem("email");

  let accessToken = localStorage.getItem("access_token");
  if (!accessToken) {
      const urlParams = new URLSearchParams(window.location.search);
      accessToken = urlParams.get("access_token");
      if (accessToken) {
          localStorage.setItem("access_token", accessToken);
      }
  }

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
          
          {email && (
  <div>

    {email === "juliusmeinlt3.3@gmail.com" && (
      <Button color="inherit" component={Link} to="/adminStart">
            Admin
          </Button>
    )}
  </div>
        )}{email && (
  <div>

    {email && (
      <Button color="inherit" component={Link} to="/profil">
            profil
          </Button>
    )}
  </div>
        )} {!email && (
  
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
