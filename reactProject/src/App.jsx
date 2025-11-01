import { HashRouter, Routes, Route } from "react-router-dom";
import HomePage from "./HomePage.jsx"
import LogIn from "./LogIn.jsx"

function App() {

  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<HomePage/>}/>
        <Route path="/logIn" element={<LogIn/>}/>
      </Routes>
    </HashRouter>
  )
}

export default App
