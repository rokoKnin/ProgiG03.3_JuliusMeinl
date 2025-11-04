import { GoogleLogin } from "@react-oauth/google"
import { useNavigate } from "react-router-dom"

function LogIn({open, onClose}) {
    const navigate = useNavigate()

    if (!open) return null
    return(
        <div>
            <h1>Log in page</h1>

            <GoogleLogin 
                onSuccess={(credentialResponse) => {
                    console.log(credentialResponse)
                    navigate("/InformationInput")
                    onClose
                }} 
                onError={() => {
                        console.log("Login failed") 
                        onClose}}/>
        </div>
    )
}

export default LogIn