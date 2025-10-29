import { BrowserRouter, Routes, Route, Link } from 'react-router-dom';
import './index.css'
function Home() {
  return(<div className='desno'><h1>Home Page</h1></div> );
}

function About() {
  return <h1>About Page</h1>;
}

function Contact() {
  return <h1>Contact Page</h1>;
}

function App() {
  return (
    
    <BrowserRouter>
      {/* Navigation */}
      <nav>
        <div className="cont">
        <div>
        <Link to="/">Home</Link> </div>
        <div>
        <Link to="/about">About</Link> </div>
        <div>
        <Link to="/contact">Contact</Link>
        </div>
        </div>
      </nav>

      {/* Routes */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/about" element={<About />} />
        <Route path="/contact" element={<Contact />} />
      </Routes>
    </BrowserRouter>
  );
}
export default App;