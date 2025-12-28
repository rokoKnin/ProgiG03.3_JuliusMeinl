
import React, { useState } from "react";
import { HashRouter, Routes, Route } from 'react-router-dom';
import './index.css';
import HomePage from "./HomePage.jsx";
import LogIn from "./LogIn.jsx";
import About from "./About.jsx";
import Contact from "./Contact.jsx";
import Header from './layouts/Header.jsx';
import ReservationSlider from './ReservationSlider.jsx';
import Dashboard from './Dashboard.jsx';
import Profil from './Profil.jsx';
import AdminStart from './AdminStart.jsx';
import Gallery from './Gallery.jsx';
import Footer from "./layouts/Footer.jsx";
export default function App() {

  const [isLoginOpen, setIsLoginOpen] = useState(false);

  const handleOpenLogin = () => setIsLoginOpen(true);
  const handleCloseLogin = () => setIsLoginOpen(false);

  return (
    <div className="appContainer">
    <HashRouter>
      <Header onOpenPopup={handleOpenLogin} />
    <div className="content">
      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/logIn" element={<LogIn/>} />
        <Route path="/about" element={<About/>} />
        <Route path="/dashboard" element={<Dashboard/>} />
        <Route path="/contact" element={<Contact/>} />
        <Route path="/reservation" element={<ReservationSlider/>} />
        <Route path="/profil" element={<Profil/>} />
        <Route path="/adminStart" element={<AdminStart/>} />
        <Route path="/gallery" element={<Gallery/>} />
      </Routes>
      </div>
      <LogIn open={isLoginOpen} onClose={handleCloseLogin} />
      <Footer />      
    </HashRouter>
    </div>
  );
}


