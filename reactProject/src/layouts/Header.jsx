import * as React from 'react';
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

import MenuIcon from '@mui/icons-material/Menu';
import { Link } from 'react-router-dom';
import LogIn from '../LogIn';

function Header() {
  const [anchorEl, setAnchorEl] = React.useState(null);
  const open = Boolean(anchorEl);
  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };
  const [isOpen, setIsOpen] = React.useState(false)

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
            <MenuItem onClick={handleClose} component={Link} to="/reservation">
              Rezervacija dodatnog sadržaja
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
          <Button color="inherit" onClick={() => setIsOpen(true)}>
            Login
          </Button>
          <LogIn open = {isOpen} onClose={() => setIsOpen(false)}>

          </LogIn>
         
        </Toolbar>
      </AppBar>
    </Box>
  );
}

export default Header;
