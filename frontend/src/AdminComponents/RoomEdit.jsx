import { Button, Checkbox, FormGroup, FormControlLabel } from "@mui/material";
import React, { useEffect, useState } from "react";
import { createPortal } from "react-dom";
import {
  Unstable_NumberInput as BaseNumberInput,
  numberInputClasses,
} from "@mui/base/Unstable_NumberInput";
import { styled } from "@mui/system";
import axios from "axios";
import FormLabel from "@mui/material/FormLabel";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";

export default function RoomEdit() {
  const [isOpen, setIsOpen] = useState(false);
  const [broj_sobe, setBrojSobe] = useState(1);
  const [brojKreveta, setBrojKreveta] = useState(2);
  const [balkon, setbalkon] = useState(false);
  const [pogledMore, setPogledMore] = useState(false);
  const [vrsta, setVrsta] = useState("DVOKREVETNA_TWIN");
  const [rendersoba, setRendersoba] = useState(null);
  const [kat, setKat] = useState(1);
  const [isEditing, setIsEditing] = useState(false);
  const [editingSoba, setEditingSoba] = useState(null);

  useEffect(() => {
    axios
      .get(`${import.meta.env.VITE_API_URL}` + '/api/rooms', { withCredentials: true })
      .then((response) => {
        setRendersoba(response.data);
      })
      .catch((error) => console.error("Error ocurred", error));
  }, []);

  const handleEditClick = (soba) => {
    setEditingSoba(soba);
    setIsEditing(true);
    setIsOpen(true);
    setBrojSobe(soba.brojSobe);
    setKat(soba.kat);
    setBrojKreveta(soba.brojKreveta || 2);
    setVrsta(soba.vrsta);
    setbalkon(soba.balkon);
    setPogledMore(soba.pogledNaMore);
  };
  const handleDelete = async (id) => {
  if (!window.confirm("Jeste li sigurni da želite obrisati ovu sobu?")) return;

  try {
    await axios.delete(`${import.meta.env.VITE_API_URL}` + `/api/rooms/${id}`, { withCredentials: true });
    setRendersoba((prev) => prev.filter((soba) => soba.id !== id));
    
  } catch (error) {
    console.error("Greška prilikom brisanja sobe:");
  }
};
  async function handleSubmit() {
    const novaSoba = {
        broj_sobe: broj_sobe,
        kat,
        vrsta,
        balkon,
        pogledMore: pogledMore,
    };

    try {
      if (isEditing && editingSoba) {
        const response = await axios.put(
            `${import.meta.env.VITE_API_URL}` + `/api/rooms/${editingSoba.id}`,
          novaSoba,
          { withCredentials: true }
        );
        setRendersoba((prev) =>
          prev.map((s) => (s.id === editingSoba.id ? response.data : s))
        );
      } else {
        const response = await axios.post(
            `${import.meta.env.VITE_API_URL}` + "/api/rooms",
          novaSoba,
          { withCredentials: true }
        );
        setRendersoba((prev) => [...prev, response.data]);
      }

      setIsOpen(false);
      setIsEditing(false);
      setEditingSoba(null);
    } catch (error) {
      console.error("Error:", error.response?.data);
    }
  }

  return (
    <div>
      <div className="nova_soba">
        <Button
          className="nova_soba_btn"
          variant="contained"
          color="primary"
          sx={{ marginTop: "10px" }}
          onClick={() => {
            setIsEditing(false);
            setEditingSoba(null);
            setBrojSobe(1);
            setKat(1);
            setBrojKreveta(2);
            setVrsta("DVOKREVETNA_TWIN");
            setbalkon(false);
            setPogledMore(false);
            setIsOpen(true);
          }}
        >
          Dodaj novu sobu
        </Button>
      </div>

      <Modal isOpen={isOpen} onClose={() => setIsOpen(false)} onSubmit={handleSubmit}>
        <div>
          Broj sobe:
          <NumberInput
            value={broj_sobe}
            onChange={(e, val) => setBrojSobe(val)}
            min={1}
            max={999}
          />
        </div>
        <div>
          Broj kata:
          <NumberInput
            value={kat}
            onChange={(e, val) => setKat(val)}
            min={1}
            max={4}
          />
        </div>
        <div>
          Broj kreveta u sobi:
          <NumberInput
            value={brojKreveta}
            onChange={(e, val) => setBrojKreveta(val)}
            min={2}
            max={3}
          />
        </div>

        {brojKreveta === 2 && (
          <div style={{ marginTop: "10px" }}>
            <FormLabel>Odaberi tip kreveta:</FormLabel>
            <RadioGroup row value={vrsta} onChange={(e) => setVrsta(e.target.value)}>
              <FormControlLabel
                value="DVOKREVETNA_TWIN"
                control={<Radio />}
                label="2 odvojena kreveta"
              />
              <FormControlLabel
                value="DVOKREVETNA_KING"
                control={<Radio />}
                label="Bračni krevet"
              />
              <FormControlLabel value="PENTAHOUSE" control={<Radio />} label="Penthouse" />
            </RadioGroup>
          </div>
        )}
        {brojKreveta === 3 && (
          <div style={{ marginTop: "10px" }}>
            <FormLabel>Odaberi tip kreveta:</FormLabel>
            <RadioGroup row value={vrsta} onChange={(e) => setVrsta(e.target.value)}>
              <FormControlLabel value="TROKREVETNA" control={<Radio />} label="Trokrevetna" />
              <FormControlLabel value="PENTAHOUSE" control={<Radio />} label="Penthouse" />
            </RadioGroup>
          </div>
        )}

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
      </Modal>

      {rendersoba &&
        rendersoba.map((soba) => (
          <div
            key={soba.id}
            style={{
              border: "1px solid gray",
              padding: "8px",
              marginBottom: "5px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <div style={{ flexGrow: 1, paddingRight: "15px" }}>
              <div>Soba broj: {soba.brojSobe}</div>
              <div>Kat: {soba.kat}</div>
              <div>Naziv sobe: {soba.vrsta}</div>
              {soba.balkon && <div>Balkon</div>}
              {soba.pogledNaMore && <div>Pogled na more</div>}
              <div>Cijena: {soba.cijena} €</div>
            </div>
            <div style={{ flexShrink: 0 }}>
              <Button
                variant="outlined"
                sx={{ color: "green", marginRight: "8px" }}
                onClick={() => handleEditClick(soba)}
              >
                Edit
              </Button>
              <Button
                variant="contained"
                color="error"
                onClick={() => handleDelete(soba.id)}
              >
                Izbriši
              </Button>
            </div>
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
        incrementButton: { children: "▴" },
        decrementButton: { children: "▾" },
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
    box-shadow: 0 0 0 3px ${
      theme.palette.mode === "dark" ? blue[600] : blue[200]
    };
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
  border: 1px solid ${
    theme.palette.mode === "dark" ? grey[800] : grey[200]
  };
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
