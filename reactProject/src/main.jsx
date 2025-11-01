import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.jsx'
import { GoogleOAuthProvider } from "@react-oauth/google"

const CLIENT_ID = "1023403202873-4m8hcvrlgr54mjoj3b81q1ts530gfki7.apps.googleusercontent.com"

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <GoogleOAuthProvider clientId={CLIENT_ID}>
      <App/>
    </GoogleOAuthProvider>
    
  </StrictMode>,
)
