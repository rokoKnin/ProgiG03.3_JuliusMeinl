import React, {useEffect, useState} from "react"
import axios from "axios";

const Dashboard = () => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/user-info', {withCredentials: true}).then(response =>
        { setUser(response.data);
        })
            .catch(error => console.error('Error ocurred', error))
    }, []);

    async function handleAddUser(){
        const ime = document.getElementById("firstname_id").value
        const prezime = document.getElementById("lastname_id").value
        const email = document.getElementById("email_id").value
        const telefon = document.getElementById("phone_id").value
        const nazMjesto = document.getElementById("place_id").value
        const postBr = document.getElementById("zipcode_id").value

        const userData = {
            ime,
            prezime,
            email,
            telefon,
            nazMjesto,
            postBr
        }

        try {
            const response = await axios.post('http://localhost:8080/api/users', userData)
            console.log('Success: ', response.data)
        } catch (error) {
            console.error('Error:', error)
        }
    }


    return(
        <div>
            <h2>Dashboard</h2>
            {user ? (
                <div>
                    <div>
                        <input type="text" placeholder="First name" id="firstname_id"></input>
                        <input type="text" placeholder="Last name" id="lastname_id"></input>
                    </div>
                    {/*<p> <strong>Email: </strong> {user.email}</p>*/}
                    <input type="text" placeholder="example@gmail.com" value={user.email} id="email_id"></input>
                    <input type="text" placeholder="Phone number" id="phone_id"></input>
                    <div>
                        <input type="text" placeholder="Mjesto" id="place_id"></input>
                        <input type="text" placeholder="Poštanski broj" id="zipcode_id"></input>
                    </div>
                    <button onClick={handleAddUser}> Confirm </button>


                </div>
            ) : (
                <p> Loading user data...</p>
            )
            }
        </div>
    );
};

export default Dashboard;