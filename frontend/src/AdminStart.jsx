import { Button} from "@mui/material";
import './index.css';
import { Link } from 'react-router-dom';
export default function AdminStart() {

    return(
      <div className="adminStartBody">
        <div className="adminStartButtons"><Button variant="contained" component={Link} to="/adminInfo">Uredi sobe</Button>
          <Button variant="contained">Izvezi statistiku</Button></div>
         
           
      </div>
    );
}