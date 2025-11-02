import { GoogleLogin } from "@react-oauth/google"
import { useNavigate } from "react-router-dom"

function LogIn() {

    const navigate = useNavigate()

    return(
        <div>
            <h1>Log in page</h1>
            <p>Ime:</p>
            <input placeholder='First name:'></input>
            <p>Prezime:</p>
            <input placeholder='Last name:'></input>
            <p>E-mail:</p>
            <input placeholder='example@gmail.com'></input>
            <p>Confirm E-mail:</p>
            <input placeholder='example@gmail.com'></input>
            <p>Password:</p>
            <input placeholder="********"></input>
            <hr/>

            <GoogleLogin 
                onSuccess={(credentialResponse) => {
                    console.log(credentialResponse)
                    navigate("/")
                }} 
                onError={() => console.log("Login failed")}/>
        </div>
    );
}

export default LogIn