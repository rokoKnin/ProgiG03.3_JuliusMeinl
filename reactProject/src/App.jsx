
import React, { useState } from "react";
import { HashRouter, Routes, Route } from 'react-router-dom';
import './index.css';
import HomePage from "./HomePage.jsx";
import LogIn from "./LogIn.jsx";
import About from "./About.jsx";
import Contact from "./Contact.jsx";
import Header from './layouts/Header.jsx';
import ReservationSlider from './ReservationSlider.jsx';
import InformationInput from './InformationInput.jsx';

export default function App() {

  const [isLoginOpen, setIsLoginOpen] = useState(false);

  const handleOpenLogin = () => setIsLoginOpen(true);
  const handleCloseLogin = () => setIsLoginOpen(false);

  return (
    <HashRouter>
      <Header onOpenPopup={handleOpenLogin} />
      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/logIn" element={<LogIn/>} />
        <Route path="/about" element={<About/>} />
        <Route path="/contact" element={<Contact/>} />
        <Route path="/reservation" element={<ReservationSlider/>} />
        <Route path="/InformationInput" element={<InformationInput/>} />
      </Routes>
      <LogIn open={isLoginOpen} onClose={handleCloseLogin} />
    </HashRouter>
  );
}


