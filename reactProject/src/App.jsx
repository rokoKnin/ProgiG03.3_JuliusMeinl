
import { HashRouter, Routes, Route } from 'react-router-dom';
import './index.css';
import HomePage from "./HomePage.jsx";
import LogIn from "./LogIn.jsx";
import About from "./About.jsx";
import Contact from "./Contact.jsx";
import Header from './layouts/Header.jsx';

export default function App() {

  return (
    <HashRouter>
      <Header />
      <Routes>
        <Route path="/" element={<HomePage/>} />
        <Route path="/logIn" element={<LogIn/>} />
        <Route path="/about" element={<About/>} />
        <Route path="/contact" element={<Contact/>} />
      </Routes>
    </HashRouter>
  );
}


