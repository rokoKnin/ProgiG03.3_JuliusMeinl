import * as React from 'react';
import './index.css';
import { Link } from 'react-router-dom';

function InformationInput() {

    return(
        <div>
            <h1>Vaši podaci</h1>
            <p>Ime:</p>
            <input placeholder='First name:'></input>
            <p>Prezime:</p>
            <input placeholder='Last name:'></input>
            <div>
                <p>Broj mobitela:</p>
                <input placeholder='+000'></input>
                <input placeholder='00-000-000'></input>
            </div>
            <Button>
            Confirm
            </Button>
        </div>
    )
}

export default InformationInput