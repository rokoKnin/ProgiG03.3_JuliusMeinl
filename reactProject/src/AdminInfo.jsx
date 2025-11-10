import { Button, Checkbox, FormGroup, FormControlLabel } from "@mui/material";
import React, {useEffect, useState} from "react"
import { createPortal } from "react-dom";
import {
    Unstable_NumberInput as BaseNumberInput,
    numberInputClasses,
} from "@mui/base/Unstable_NumberInput";
import { color, styled } from "@mui/system";
import axios from "axios";

export default function AdminInfo() {
    const [isOpen, setIsOpen] = useState(false);
    const [brojSobe, setBrojSobe] = useState(1);
    const [brojKreveta, setBrojKreveta] = useState(2);
    const [balkon, setbalkon] = useState(false);
    const [pogledMore, setPogledMore] = useState(false);
    const [cijenaSobe, setCijenaSobe] = useState(0);
    const [sobe, setSobe] = useState([]);

    const [rendersoba, setRendersoba] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/sobe/vrsta/DVOKREVETNA_TWIN', {withCredentials: true}).then(response =>
        { setRendersoba(response.data);
        })
            .catch(error => console.error('Error ocurred', error))
    }, []);


    const handleSubmit = () => {


        const novaSoba = {
            brojSobe,
            brojKreveta,
            balkon,
            pogledMore,
            cijenaSobe,
        };

        setSobe((prev) => [...prev, novaSoba]);
        setIsOpen(false);
    };
    console.log(rendersoba);
    return (
        <div>
            <div className="nova_soba">
                <Button
                    className="nova_soba_btn"
                    variant="contained"
                    color="primary"
                    sx={{ marginTop: "10px" }}
                    onClick={() => setIsOpen(true)}
                >
                    Dodaj novu sobu
                </Button>

                {sobe.map((soba) => (
                    <div className="soba_info_box" style={{ marginTop: 10,color: "gray", border: "1px solid gray", padding: "10px", borderRadius: "8px" }}>
                        <div>Soba broj: {soba.brojSobe}</div>
                        <div>Broj kreveta: {soba.brojKreveta}</div>
                        {(soba.balkon||soba.pogledMore) && <div>Dodatni atributi:</div>}
                        {soba.balkon && <div>balkon</div>}
                        {soba.pogledMore && <div>Pogled na more</div>}
                        <div>Cijena sobe po noćenju: {soba.cijenaSobe} eur</div>
                    </div>
                ))}
            </div>

            <Modal isOpen={isOpen} onClose={() => setIsOpen(false)} onSubmit={handleSubmit}>
                <div>Broj sobe:
                    <NumberInput
                        value={brojSobe}
                        onChange={(e, val) => setBrojSobe(val)}
                        min={1}
                        max={999}
                    /></div>

                <div>Broj kreveta u sobi:
                    <NumberInput
                        value={brojKreveta}
                        onChange={(e, val) => setBrojKreveta(val)}
                        min={2}
                        max={3}
                    /></div>

                <h2 style={{ color: blue[600] }}>Dodatni atributi:</h2>
                <FormGroup>
                    <FormControlLabel
                        control={<Checkbox checked={balkon} onChange={(e) => setbalkon(e.target.checked)} />}
                        label="Balkon"
                    />
                    <FormControlLabel
                        control={
                            <Checkbox checked={pogledMore} onChange={(e) => setPogledMore(e.target.checked)} />
                        }
                        label="Pogled na more"
                    />
                </FormGroup>
                <div> Cijena sobe po noćenju:
                    <NumberInput
                        value={cijenaSobe}
                        onChange={(e, val) => setBrojKreveta(val)}
                        min={0}
                    /></div>
            </Modal>

            <div>Dvokrevetna twin soba</div>
            {rendersoba && rendersoba.map((soba) => (
                <div key={soba.id} style={{ border: "1px solid gray", padding: "8px", marginBottom: "5px" }}>
                    <div>Soba broj: {soba.brojSobe}</div>
                    <div>Broj kreveta: {soba.brojKreveta}</div>
                    {soba.balkon && <div>Balkon</div>}
                    {soba.pogledMore && <div>Pogled na more</div>}
                    <div>Cijena: {soba.cijena} €</div>
                </div>
            ))}
        </div>
    );
}

function Modal({ isOpen, onClose, onSubmit, children }) {
    if (!isOpen) return null;

    return createPortal(
        <div
            style={{
                position: "fixed",
                top: 0,
                left: 0,
                right: 0,
                bottom: 0,
                backgroundColor: "rgba(0, 0, 0, 0.5)",
                display: "flex",
                alignItems: "center",
                justifyContent: "center",
            }}
        >
            <div
                style={{
                    background: "white",
                    padding: "20px",
                    borderRadius: "8px",
                    minWidth: "300px",
                }}
            >
                {children}
                <Button sx={{ marginTop: "10px", marginRight: "8px" }} variant="outlined" onClick={onClose}>
                    Odustani
                </Button>
                <Button
                    sx={{ marginTop: "10px", marginRight: "8px" }}
                    color="primary"
                    variant="contained"
                    onClick={onSubmit}
                >
                    Spremi
                </Button>
            </div>
        </div>,
        document.body
    );
}

const NumberInput = React.forwardRef(function CustomNumberInput(props, ref) {
    return (
        <BaseNumberInput
            slots={{
                root: StyledInputRoot,
                input: StyledInputElement,
                incrementButton: StyledButton,
                decrementButton: StyledButton,
            }}
            slotProps={{
                incrementButton: {
                    children: "▴",
                },
                decrementButton: {
                    children: "▾",
                },
            }}
            {...props}
            ref={ref}
        />
    );
});

const blue = {
    100: "#DAECFF",
    200: "#80BFFF",
    400: "#3399FF",
    500: "#007FFF",
    600: "#0072E5",
};

const grey = {
    50: "#F3F6F9",
    100: "#E5EAF2",
    200: "#DAE2ED",
    300: "#C7D0DD",
    400: "#B0B8C4",
    500: "#9DA8B7",
    600: "#6B7A90",
    700: "#434D5B",
    800: "#303740",
    900: "#1C2025",
};

const StyledInputRoot = styled("div")(
    ({ theme }) => `
  font-family: 'IBM Plex Sans', sans-serif;
  font-weight: 400;
  border-radius: 8px;
  color: ${theme.palette.mode === "dark" ? grey[300] : grey[900]};
  background: ${theme.palette.mode === "dark" ? grey[900] : "#fff"};
  border: 1px solid ${theme.palette.mode === "dark" ? grey[700] : grey[200]};
  box-shadow: 0 2px 2px ${theme.palette.mode === "dark" ? grey[900] : grey[50]};
  display: grid;
  grid-template-columns: 1fr 19px;
  grid-template-rows: 1fr 1fr;
  overflow: hidden;
  column-gap: 8px;
  padding: 4px;

  &.${numberInputClasses.focused} {
    border-color: ${blue[400]};
    box-shadow: 0 0 0 3px ${theme.palette.mode === "dark" ? blue[600] : blue[200]};
  }

  &:hover {
    border-color: ${blue[400]};
  }
`
);

const StyledInputElement = styled("input")`
    font-size: 0.875rem;
    font-family: inherit;
    font-weight: 400;
    line-height: 1.5;
    grid-column: 1/2;
    grid-row: 1/3;
    color: inherit;
    background: inherit;
    border: none;
    border-radius: inherit;
    padding: 8px 12px;
    outline: 0;
`;

const StyledButton = styled("button")(
    ({ theme }) => `
  display: flex;
  justify-content: center;
  align-items: center;
  width: 19px;
  height: 19px;
  border: 1px solid ${theme.palette.mode === "dark" ? grey[800] : grey[200]};
  background: ${theme.palette.mode === "dark" ? grey[900] : grey[50]};
  color: ${theme.palette.mode === "dark" ? grey[200] : grey[900]};
  cursor: pointer;

  &:hover {
    background: ${blue[400]};
    color: white;
  }

  &.${numberInputClasses.incrementButton} {
    grid-column: 2/3;
    grid-row: 1/2;
    border-top-left-radius: 4px;
    border-top-right-radius: 4px;
  }

  &.${numberInputClasses.decrementButton} {
    grid-column: 2/3;
    grid-row: 2/3;
    border-bottom-left-radius: 4px;
    border-bottom-right-radius: 4px;
  }
`
);
