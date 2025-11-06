import { GoogleLogin } from "@react-oauth/google"
import { useNavigate } from "react-router-dom"

function LogIn({open, onClose}) {
    const navigate = useNavigate()

    if (!open) return null

    const handleSuccess = (credentialResponse) => {
    console.log("Login successful:", credentialResponse);
    onClose()
    navigate("/InformationInput")
    }
    const googleLogin = () => {
        window.location.href= 'http://localhost:8080/oauth2/authorization/google'
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

                <button onClick={googleLogin}> Login with Google </button>
                <button
                onClick={onClose}
                style={{
                    marginTop: "1.5rem",
                    background: "#1976d2",
                    color: "white",
                    border: "none",
                    padding: "0.6rem 1rem",
                    borderRadius: "4px",
                    cursor: "pointer",
                }}
                >
                Close
                </button>
            </div>
        </div>
    )
}

export default LogIn