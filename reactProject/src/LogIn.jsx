import { GoogleLogin } from "@react-oauth/google"
import { useNavigate } from "react-router-dom"

import Button from '@mui/material/Button';
function LogIn({open, onClose}) {
    const navigate = useNavigate()

    if (!open) return null

    const handleSuccess = (credentialResponse) => {
    console.log("Login successful:", credentialResponse);
    onClose()
    navigate("/InformationInput")
    }
    const googleLogin = () => {
        window.location.href= 'https://juliusmeinl.onrender.com/oauth2/authorization/google'
    }

    const handleError = () => {
    console.log("Login failed")
    onClose()
    }
    return(
        <div
            style={{
                position: "fixed",
                inset: 0,
                backgroundColor: "rgba(0,0,0,0.5)",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
                zIndex: 1300,
            }}
        >
            <div
                style={{
                background: "white",
                padding: "2rem",
                borderRadius: "8px",
                textAlign: "center",
                width: "320px",
                }}
            >
                <h2>Log in</h2>
                <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="contained" onClick={googleLogin}>Login with Google</Button>
                 <Button sx={{marginTop:"10px",marginRight:"8px"}} color="primary" variant="outlined" onClick={onClose}>Close</Button>
         
     
            </div>
        </div>
    )
}

export default LogIn