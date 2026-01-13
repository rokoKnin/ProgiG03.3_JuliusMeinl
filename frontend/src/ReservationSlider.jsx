import React, { useState } from "react";
import Box from "@mui/material/Box";
import Stepper from "@mui/material/Stepper";
import Step from "@mui/material/Step";
import StepLabel from "@mui/material/StepLabel";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import { Link } from "react-router-dom";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import dayjs from "dayjs";
import { DemoContainer } from "@mui/x-date-pickers/internals/demo";
import axios from "axios";
import { styled } from "@mui/system";
import KingBedOutlinedIcon from "@mui/icons-material/KingBedOutlined";
import SingleBedOutlinedIcon from "@mui/icons-material/SingleBedOutlined";
import RemoveIcon from "@mui/icons-material/Remove";
import AddIcon from "@mui/icons-material/Add";
import LivingOutlinedIcon from "@mui/icons-material/LivingOutlined";
import BalconyOutlinedIcon from "@mui/icons-material/BalconyOutlined";
import TsunamiOutlinedIcon from "@mui/icons-material/TsunamiOutlined";
import { Unstable_NumberInput as BaseNumberInput } from "@mui/base/Unstable_NumberInput";
import ReservationAdditionalServices from "./ReservationAdditionalServices";

const NumberInput = React.forwardRef(function CustomNumberInput(props, ref) {
    return (
        <BaseNumberInput
            slots={{
                root: StyledInputRoot,
                input: StyledInput,
                incrementButton: StyledButton,
                decrementButton: StyledButton,
            }}
            slotProps={{
                incrementButton: { children: <AddIcon fontSize="small" />, className: "increment" },
                decrementButton: { children: <RemoveIcon fontSize="small" /> },
            }}
            {...props}
            ref={ref}
        />
    );
});

const steps = ["Datum i broj gostiju", "Odabir dostupne sobe", "Dodatni sadržaji"];

export default function ReservationSlider() {
    const [activeStep, setActiveStep] = useState(0);
    const [skipped, setSkipped] = useState(new Set());
    const [datumDolaska, setDatumDolaska] = useState(dayjs().startOf("day"));
    const [datumOdlaska, setDatumOdlaska] = useState(dayjs().add(1, "day"));
    const [datumDanas] = useState(dayjs().startOf("day"));
    const [slobodneSobe, setSlobodneSobe] = useState([]);
    const [sobeKolicina, setSobeKolicina] = useState({}); // {roomId: quantity}
    const [dodatniSadrzaj, setDodatniSadrzaj] = useState([[], [], []]);
    const [loading, setLoading] = useState(false);

    const prikazSoba = {
        DVOKREVETNA_KING: { label: "Dvokrevetna king soba", icon: <KingBedOutlinedIcon /> },
        TROKREVETNA: { label: "Trokrevetna soba", icon: <><KingBedOutlinedIcon /><SingleBedOutlinedIcon /></> },
        DVOKREVETNA_TWIN: { label: "Dvokrevetna twin soba", icon: <><SingleBedOutlinedIcon /><SingleBedOutlinedIcon /></> },
        PENTHOUSE: { label: "Penthouse", icon: <><KingBedOutlinedIcon /><LivingOutlinedIcon /></> },
        Balkon: { label: "Balkon", icon: <BalconyOutlinedIcon /> },
        "Pogled na more": { label: "Pogled na more", icon: <TsunamiOutlinedIcon /> },
    };

    const isStepOptional = (step) => step === 2;
    const isStepSkipped = (step) => skipped.has(step);

    // POST available rooms for selected dates
    async function postDates(datumOd, datumDo) {
        setLoading(true);
        try {
            const response = await axios.post(`${import.meta.env.VITE_API_URL}/api/rooms/available`, { datumOd, datumDo }, { withCredentials: true });
            setSlobodneSobe(response.data);
            // Initialize quantities to 0
            const initialQuantities = {};
            response.data.forEach((soba) => {
                initialQuantities[soba.id] = 0;
            });
            setSobeKolicina(initialQuantities);
            return true;
        } catch (error) {
            console.error(error);
            setSlobodneSobe([]);
            return false;
        } finally {
            setLoading(false);
        }
    }

    // POST reservation with rooms and additional services
    async function postReservation() {
        const roomsSelected = slobodneSobe
            .filter((soba) => sobeKolicina[soba.id] > 0)
            .map((soba) => ({ ...soba, quantity: sobeKolicina[soba.id] }));

        const formattedServices = dodatniSadrzaj.flat().map((item) => ({
            ...item,
            datum: dayjs(item.datum).format("YYYY-MM-DD"),
        }));

        try {
            return await axios.post(
                `${import.meta.env.VITE_API_URL}/api/reservations`,
                {
                    datumOd: datumDolaska.format("YYYY-MM-DD"),
                    datumDo: datumOdlaska.format("YYYY-MM-DD"),
                    odabraneSobe: roomsSelected,
                    odabraniDodatniSadrzaj: formattedServices,
                },
                { withCredentials: true }
            );
        } catch (error) {
            console.error(error);
            return false;
        }
    }

    const handleNext = async () => {
        let newSkipped = skipped;
        if (isStepSkipped(activeStep)) {
            newSkipped = new Set(newSkipped.values());
            newSkipped.delete(activeStep);
        }

        if (activeStep === 0) {
            if (!datumDolaska || !datumOdlaska || datumDolaska.isSameOrAfter(datumOdlaska, "day")) {
                alert("Datum dolaska mora biti prije datuma odlaska!");
                return;
            }
            const success = await postDates(datumDolaska.format("YYYY-MM-DD"), datumOdlaska.format("YYYY-MM-DD"));
            if (!success) return;
        }

        if (activeStep === 1) {
            const totalRooms = Object.values(sobeKolicina).reduce((a, b) => a + b, 0);
            if (totalRooms === 0) {
                alert("Morate odabrati barem jednu sobu.");
                return;
            }
            if (totalRooms > 5) {
                alert("Maksimalno je 5 soba.");
                return;
            }
        }

        if (activeStep === 2) {
            const response = await postReservation();
            if (!response) {
                alert("Došlo je do greške prilikom rezervacije.");
                return;
            }
        }

        setActiveStep((prev) => prev + 1);
        setSkipped(newSkipped);
    };

    const handleBack = () => setActiveStep((prev) => prev - 1);
    const handleSkip = () => {
        if (!isStepOptional(activeStep)) throw new Error("Ne možete preskočiti ovaj korak.");
        setActiveStep((prev) => prev + 1);
        setSkipped((prev) => new Set(prev).add(activeStep));
    };
    const handleReset = () => setActiveStep(0);

    const handleAdditionalUpdate = (lista) => setDodatniSadrzaj(lista);

    // Step content
    let content;
    if (activeStep === 0) {
        content = (
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={["DatePicker", "DatePicker"]}>
                    <DatePicker
                        label="Datum dolaska"
                        value={datumDolaska}
                        minDate={datumDanas}
                        onChange={(val) => setDatumDolaska(val)}
                        format="DD.MM.YYYY"
                    />
                    <DatePicker
                        label="Datum odlaska"
                        value={datumOdlaska}
                        minDate={datumDolaska.add(1, "day")}
                        onChange={(val) => setDatumOdlaska(val)}
                        format="DD.MM.YYYY"
                    />
                </DemoContainer>
            </LocalizationProvider>
        );
    } else if (activeStep === 1) {
        content =
            slobodneSobe.length > 0 ? (
                slobodneSobe.map((soba) => {
                    const info = prikazSoba[soba.vrsta];
                    return (
                        <Box
                            key={soba.id}
                            sx={{
                                border: "1px solid gray",
                                padding: 2,
                                marginBottom: 1,
                                display: "flex",
                                justifyContent: "space-between",
                                alignItems: "center",
                            }}
                        >
                            <Box>
                                <Typography>
                                    {info.label} {info.icon}
                                </Typography>
                                {soba.balkon && (
                                    <Typography>
                                        {prikazSoba.Balkon.label} {prikazSoba.Balkon.icon}
                                    </Typography>
                                )}
                                {soba.pogledNaMore && (
                                    <Typography>
                                        {prikazSoba["Pogled na more"].label} {prikazSoba["Pogled na more"].icon}
                                    </Typography>
                                )}
                                <Typography>Cijena: {soba.cijena} €</Typography>
                            </Box>
                            <NumberInput
                                value={sobeKolicina[soba.id]}
                                onChange={(event, val) => {
                                    if (val > soba.brojDostupnih) {
                                        alert("Nema toliko soba dostupno!");
                                        return;
                                    }
                                    setSobeKolicina((prev) => ({ ...prev, [soba.id]: val }));
                                }}
                                min={0}
                                max={soba.brojDostupnih}
                            />
                        </Box>
                    );
                })
            ) : (
                <Typography>Nema slobodnih soba za odabrane datume.</Typography>
            );
    } else {
        content = <ReservationAdditionalServices showNext={false} onUpdate={handleAdditionalUpdate} />;
    }

    return (
        <Box sx={{ width: "100%" }}>
            <Stepper activeStep={activeStep}>
                {steps.map((label, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    if (isStepOptional(index)) labelProps.optional = <Typography variant="caption">Optional</Typography>;
                    if (isStepSkipped(index)) stepProps.completed = false;
                    return (
                        <Step key={label} {...stepProps}>
                            <StepLabel {...labelProps}>{label}</StepLabel>
                        </Step>
                    );
                })}
            </Stepper>

            {activeStep === steps.length ? (
                <React.Fragment>
                    <Typography sx={{ mt: 2, mb: 1 }}>
                        Uspješno ste napravili rezervaciju. Veselimo se vašem dolasku :)
                    </Typography>
                    <Box sx={{ display: "flex", pt: 2 }}>
                        <Box sx={{ flex: "1 1 auto" }} />
                        <Button onClick={handleReset} component={Link} to="/profil" variant="contained">
                            Pregled Rezervacije
                        </Button>
                    </Box>
                </React.Fragment>
            ) : (
                <React.Fragment>
                    <Typography sx={{ mt: 2, mb: 1 }}>{content}</Typography>
                    <Box sx={{ display: "flex", flexDirection: "row", pt: 2 }}>
                        <Button variant="outlined" disabled={activeStep === 0} onClick={handleBack} sx={{ mr: 1 }}>
                            Back
                        </Button>
                        <Box sx={{ flex: "1 1 auto" }} />
                        {isStepOptional(activeStep) && (
                            <Button onClick={handleSkip} sx={{ mr: 1 }}>
                                Skip
                            </Button>
                        )}
                        <Button onClick={handleNext} variant="contained" disabled={loading}>
                            {activeStep === steps.length - 1 ? "Finish" : "Next"}
                        </Button>
                    </Box>
                </React.Fragment>
            )}
        </Box>
    );
}

// Styled Components
const blue = { 400: "#3399ff", 500: "#007fff", 700: "#0059B2" };
const grey = { 50: "#F3F6F9", 200: "#DAE2ED", 500: "#9DA8B7", 900: "#1C2025" };

const StyledInputRoot = styled("div")(({ theme }) => `
  font-family: 'IBM Plex Sans', sans-serif;
  display: flex;
  justify-content: center;
  align-items: center;
`);

const StyledInput = styled("input")(({ theme }) => `
  width: 60px;
  text-align: center;
  font-size: 0.875rem;
  padding: 8px;
  margin: 0 8px;
  border: 1px solid ${grey[200]};
  border-radius: 8px;
`);

const StyledButton = styled("button")(({ theme }) => `
  width: 32px;
  height: 32px;
  border-radius: 999px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 1px solid ${grey[200]};
  background: ${grey[50]};
  cursor: pointer;
  &.increment { order: 1; }
  &:hover { background: ${blue[500]}; color: white; border-color: ${blue[400]}; }
`);
