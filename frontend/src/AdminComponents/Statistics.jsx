export default function Statistics() {
    return (
        /*<div>
            <h2>Statistika</h2>
            <p>Ovdje će ići statistički podaci o korisnicima, rezervacijama i slično.</p>
        </div>*/
        <div className="Statistics" style= {{display: "flex", flexWrap: "wrap", gap: "1rem", justifyContent: "space-between"}}>
            <div className="dataCard" style={{width: "100%", height: "20rem", background: "white"}}>Graf 1</div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>Graf 2</div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>Graf 3</div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>Graf 4</div>
            <div className="dataCard" style={{width: "45%", height: "20rem", background: "white"}}>Graf 5</div>
        </div>
    );
}
