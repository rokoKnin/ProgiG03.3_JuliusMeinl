import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'

import App from './App.jsx'
import { GoogleOAuthProvider } from "@react-oauth/google"

const CLIENT_ID = "1003142787779-8cbkk1bekcsh3o8phfsoj2sor1jg2t7p.apps.googleusercontent.com"

createRoot(document.getElementById('root')).render(
  <StrictMode>
    <GoogleOAuthProvider clientId={CLIENT_ID}>
      <App/>
    </GoogleOAuthProvider>
    
  </StrictMode>,
)
