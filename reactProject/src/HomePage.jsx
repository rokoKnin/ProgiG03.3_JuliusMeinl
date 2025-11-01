import { useNavigate } from "react-router-dom"

function HomePage() {
    
    const navigate = useNavigate()
    const handleClick = () => {
        navigate("/logIn")
    }
    return (
        <div>
            <button onClick={handleClick}>Log in</button>
        </div>
    )
}

export default HomePage