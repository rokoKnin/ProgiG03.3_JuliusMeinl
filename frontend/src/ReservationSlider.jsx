import React, { useEffect, useState } from "react";
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Reservation from './Reservation';
import ReservationAdditionalServices from './ReservationAdditionalServices';
import { colors } from '@mui/material';
import { Link, useNavigate } from 'react-router-dom';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import axios from "axios";
import { styled } from '@mui/system';
import KingBedOutlinedIcon from '@mui/icons-material/KingBedOutlined';
import SingleBedOutlinedIcon from '@mui/icons-material/SingleBedOutlined';
import RemoveIcon from '@mui/icons-material/Remove';
import AddIcon from '@mui/icons-material/Add';
import LivingOutlinedIcon from '@mui/icons-material/LivingOutlined';
import BalconyOutlinedIcon from '@mui/icons-material/BalconyOutlined';
import { Unstable_NumberInput as BaseNumberInput } from '@mui/base/Unstable_NumberInput';
import TsunamiOutlinedIcon from '@mui/icons-material/TsunamiOutlined';
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
                incrementButton: {
                    children: <AddIcon fontSize="small" />,
                    className: 'increment'
                },
                decrementButton: {
                    children: <RemoveIcon fontSize="small" />,
                },
            }}
            {...props}
            ref={ref}
        />
    );
});
const steps = ['Datum i broj gostiju', 'Odabir dostupne sobe', 'Dodatni sadržaji'];

export default function HorizontalLinearStepper() {
    const [activeStep, setActiveStep] = React.useState(0);
    const [skipped, setSkipped] = React.useState(new Set());
    const[datumDolaska,setDatumDolaska]=React.useState(dayjs().startOf('day'));
    const[datumDanas,setDatumDanas]=React.useState(dayjs().startOf('day'));
    const[datumOdlaska,setDatumOdlaska]=React.useState(dayjs().add(1, 'day'));
    const[slobodneSobe,setSlobodneSobe]=React.useState(null);
    const[brojOdabranoSoba,setBrojOdabranoSoba]=React.useState([0]);
    const[odabraneSobe,setOdabraneSobe]=React.useState([]);
    const[totalOdabranih,setTotalOdabranih]=React.useState(0);
    const navigate=useNavigate();
    const[dodatniSadrzaj,SetDodatniSadrzaj]=React.useState([[],[],[]]);
    {/*useEffect(() => {
  if(activeStep==1){
    axios
      .get(`${import.meta.env.VITE_API_URL}` + 'api/room-reservation/available', { withCredentials: true })
      .then((responseSoba) => {
        setSlobodneSobe(responseSoba.data);
        setOdabranoSoba(new Array(responseSoba.data.length).fill(0) )
      })
      .catch((error) => {console.log("myb nije povezano ");;
    setSlobodneSobe(null)});

  }}, [activeStep]);*/}

    const isStepOptional = (step) => {
        return step === 2;
    };

    const isStepSkipped = (step) => {
        return skipped.has(step);
    };

    const prikazSoba={
        "DVOKREVETNA_KING":{label:"Dvokrevetna king soba", icon:<KingBedOutlinedIcon/>},
        "TROKREVETNA":{label:"Trokrevetna soba", icon:(<><KingBedOutlinedIcon/><SingleBedOutlinedIcon/></>)},
        "DVOKREVETNA_TWIN":{label:"Dvokrevetna twin soba", icon:(<><SingleBedOutlinedIcon/><SingleBedOutlinedIcon/></>)},
        "PENTHOUSE":{label:"Penthouse", icon:(<><KingBedOutlinedIcon/><LivingOutlinedIcon/></>)},
        "Balkon":{label:"Balkon",icon:<BalconyOutlinedIcon/>},
        "Pogled na more":{label:"Pogled na more",icon:<TsunamiOutlinedIcon/>}

    }
    async function postDates(datumOd, datumDo){
        const datumi={
            datumOd,
            datumDo
        }
        try {
            const responseSoba= await axios.post(`${import.meta.env.VITE_API_URL}` + '/api/rooms/available', datumi,  {withCredentials: true} )
            setSlobodneSobe(responseSoba.data);
            setBrojOdabranoSoba(new Array(responseSoba.data.length).fill(0));
            return true;
        } catch (error) {
            console.error('Error: nije se poslao post zbog necega', error.response?.data)
            console.error(error.response?.status)
            console.error(error.response)
            setSlobodneSobe(null);
            return false;
        }

    }
    {/*async function postSobeDodatniSadrzaj( datumOd,datumDo,odabraniDodatniSadrzaj, odabraneSobe){
    const sadrzaj={
      datumOd,
      datumDo,
      odabraneSobe,
      odabraniDodatniSadrzaj
    }
    console.log(sadrzaj);
    try {

                 return await axios.post(`${import.meta.env.VITE_API_URL}` + '/api/reservations', sadrzaj,  {withCredentials: true} )


              } catch (error) {
                  console.error('Error: nije se poslao post zbog necega', error.response?.data)

                  return false;
                }

  }*/}

    const handleFinish=()=>{
        const formatirano=dodatniSadrzaj.flat().map((kat,i)=>({
                ...kat,datum:dayjs(kat.datum).format('YYYY-MM-DD')
            })
        );
        const sadrzaj={
            datumOd:datumDolaska.format("YYYY-MM-DD"),
            datumDo:datumOdlaska.format("YYYY-MM-DD"),
            odabraneSobe,
            odabraniDodatniSadrzaj: formatirano
        }

        navigate('/payment',{state:sadrzaj});
    }
    const handleNext = async () => {
        let newSkipped = skipped;
        if (isStepSkipped(activeStep)) {
            newSkipped = new Set(newSkipped.values());
            newSkipped.delete(activeStep);
        }
        if(activeStep===0){
            if(datumDolaska.isAfter(datumOdlaska)){
                alert("Datum dolaska ne može biti nakon datuma odlaska!");

                return;
            }
            if(datumDolaska.isSame(datumOdlaska,'day')){
                alert("Datum dolaska ne može biti jednak datumu odlaska!");
                return;
            }
            await postDates(datumDolaska.format('YYYY-MM-DD'), datumOdlaska.format('YYYY-MM-DD'));

        }
        if(activeStep===1){
            {/*potrebno ograničenje u broju soba */}

            if(totalOdabranih>5){
                alert("Nažalost, nije moguće rezervirati više o 5 soba.");
                return;
            }
            if(totalOdabranih===0){
                alert("Morate odabrati barem jednu sobu za rezervaciju.");
                return;
            }}
        if(activeStep===2){
            {/*const formatirano=dodatniSadrzaj.flat().map((kat,i)=>({
            ...kat,datum:dayjs(kat.datum).format('YYYY-MM-DD')
          })
        );
      const uspjeh=await postSobeDodatniSadrzaj(datumDolaska.format('YYYY-MM-DD'), datumOdlaska.format('YYYY-MM-DD'),formatirano,odabraneSobe);
      if(!uspjeh){
        console.log("greska");
      }*/}
        }

        setActiveStep((prevActiveStep) => prevActiveStep + 1);
        setSkipped(newSkipped);
    };

    const handleBack = () => {
        setActiveStep((prevActiveStep) => prevActiveStep - 1);
    };

    const handleSkip = () => {
        if (!isStepOptional(activeStep)) {
            throw new Error("You can't skip a step that isn't optional.");
        }

        setActiveStep((prevActiveStep) => prevActiveStep + 1);
        setSkipped((prevSkipped) => {
            const newSkipped = new Set(prevSkipped.values());
            newSkipped.add(activeStep);
            return newSkipped;
        });
    };

    const handleReset = () => {
        setActiveStep(0);
    };
    let content;
    const handleAdditional=(lista)=>{

        SetDodatniSadrzaj(lista);


    };
    if (activeStep+1 === 1) {
        content =<div> <h3>Odaberite datum</h3>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DemoContainer components={['DatePicker', 'DatePicker']}>
                    <DatePicker label="Datum dolaska" name="datumDolaska" minDate={datumDanas}value={datumDolaska} onChange={(newValue) => setDatumDolaska(newValue)} format="DD.MM.YYYY" />
                    <DatePicker label="Datum odlaska" name="datumOdlaska" minDate={datumDanas.add(1, 'day')}value={datumOdlaska} onChange={(newValue) => setDatumOdlaska(newValue)} format="DD.MM.YYYY" />
                </DemoContainer>
            </LocalizationProvider></div>;
    } else if (activeStep+1 ===2) {

        if(slobodneSobe){
            content=slobodneSobe.map((soba,i)=>
            {
                const info=prikazSoba[soba.vrsta];
                const balkon=prikazSoba["Balkon"];
                const pogledNaMore=prikazSoba["Pogled na more"];
                return(

                    <div key={i}style={{
                        border: "1px solid gray",
                        padding: "8px",
                        marginBottom: "5px",
                        display: "flex",
                        justifyContent: "space-between",
                        alignItems: "center",
                    }}
                    >
                        <div style={{ flexGrow: 1, paddingRight: "15px", display:"flex",flexDirection:"row",justifyContent:"space-between",alignItems:"center" }}>
                            <div>

                                <div style={{display:"flex",flexDirection:"row", gap:"5px"}}>Naziv sobe: {info.label} {info.icon}</div>
                                {soba.balkon && <div style={{display:"flex",flexDirection:"row", gap:"5px"}}>{balkon.label}{balkon.icon}</div>}
                                {soba.pogledNaMore && <div style={{display:"flex",flexDirection:"row", gap:"5px"}}>{pogledNaMore.label}{pogledNaMore.icon}</div>}
                                <div>Cijena: {soba.cijena} €</div></div>
                            <div>
                                <NumberInput value={brojOdabranoSoba[i]||0} onChange={(event,val)=>{
                                    const prethodna=brojOdabranoSoba[i]||0;

                                    if(val>soba.brojDostupnih){
                                        alert("Nije dostupno toliko soba te vrste");
                                        return;
                                    }
                                    const dodano=[...brojOdabranoSoba];
                                    dodano[i]=val;
                                    let zb=0;
                                    for(let ii=0;ii<dodano.length;ii++){
                                        if(dodano[ii]){
                                            zb+=dodano[ii];
                                        }
                                    }
                                    if(prethodna<val){
                                        setOdabraneSobe((prev)=>[...prev,soba]);
                                    }else{
                                        setOdabraneSobe((prev)=>
                                            {
                                                const ind=prev.findIndex(s=>s===soba)
                                                if(ind!==-1){
                                                    const novi=[...prev]
                                                    novi.splice(ind,1);
                                                    return novi;
                                                }
                                                return prev;
                                            }

                                        );
                                    }
                                    setTotalOdabranih(zb);
                                    setBrojOdabranoSoba(dodano);
                                }} min={0} max={5} defaultValue={0}  />
                            </div>
                        </div>
                    </div>
                )});

        }
        else{
            content= <span>Nema odgovarajućih soba na taj datum</span>
        }

    } else {
        content = <ReservationAdditionalServices showNext={false} onUpdate={handleAdditional}/>;
    }


    return (
        <Box sx={{ width: '100%' }}>
            <Stepper activeStep={activeStep}>
                {steps.map((label, index) => {
                    const stepProps = {};
                    const labelProps = {};
                    if (isStepOptional(index)) {
                        labelProps.optional = (
                            <Typography variant="caption">Optional</Typography>
                        );
                    }
                    if (isStepSkipped(index)) {
                        stepProps.completed = false;
                    }
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
                        <div>Uspješno ste napravili rezervaciju. Veselimo se vašem dolasku :)</div>

                    </Typography>
                    <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
                        <Box sx={{ flex: '1 1 auto' }} />
                        <Button onClick={handleReset} style={{color: "#e4e8ecff"}} component={Link} to="/profil" >Pregled Rezervacije</Button>
                    </Box>
                </React.Fragment>
            ) : (
                <React.Fragment>
                    <Typography sx={{ mt: 2, mb: 1 }}>{content}</Typography>
                    <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
                        <Button
                            variant="outlined"
                            style={{color: "#070c10ff"}}
                            disabled={activeStep === 0}
                            onClick={handleBack}
                            sx={{ mr: 1 }}
                        >
                            Back
                        </Button>
                        <Box sx={{ flex: '1 1 auto' }} />
                        {isStepOptional(activeStep) && (
                            <Button style={{color: "#070c10ff"}} onClick={handleSkip} sx={{ mr: 1 }}>
                                Skip
                            </Button>
                        )}
                        {activeStep === steps.length - 1 &&
                            <Button  variant="contained" style={{color: "#e4e8ecff"}} onClick={handleFinish} >Finish</Button>
                        }
                        {activeStep !== steps.length - 1 &&
                            <Button  onClick={handleNext}variant="contained" style={{color: "#e4e8ecff"}}>Next</Button>
                        }
                    </Box>
                </React.Fragment>
            )}
        </Box>
    );
}

const blue = {
    100: '#daecff',
    200: '#b6daff',
    300: '#66b2ff',
    400: '#3399ff',
    500: '#007fff',
    600: '#0072e5',
    700: '#0059B2',
    800: '#004c99',
};

const grey = {
    50: '#F3F6F9',
    100: '#E5EAF2',
    200: '#DAE2ED',
    300: '#C7D0DD',
    400: '#B0B8C4',
    500: '#9DA8B7',
    600: '#6B7A90',
    700: '#434D5B',
    800: '#303740',
    900: '#1C2025',
};
const StyledInputRoot = styled('div')(
    ({ theme }) => `
  font-family: 'IBM Plex Sans', sans-serif;
  font-weight: 400;
  color: ${theme.palette.mode === 'dark' ? grey[300] : grey[500]};
  display: flex;
  flex-flow: row nowrap;
  justify-content: center;
  align-items: center;
`,
);

const StyledInput = styled('input')(
    ({ theme }) => `
  font-size: 0.875rem;
  font-family: inherit;
  font-weight: 400;
  line-height: 1.375;
  color: ${theme.palette.mode === 'dark' ? grey[300] : grey[900]};
  background: ${theme.palette.mode === 'dark' ? grey[900] : '#fff'};
  border: 1px solid ${theme.palette.mode === 'dark' ? grey[700] : grey[200]};
  box-shadow: 0 2px 4px ${
        theme.palette.mode === 'dark' ? 'rgba(0,0,0, 0.5)' : 'rgba(0,0,0, 0.05)'
    };
  border-radius: 8px;
  margin: 0 8px;
  padding: 10px 12px;
  outline: 0;
  min-width: 0;
  width: 4rem;
  text-align: center;

  &:hover {
    border-color: ${blue[400]};
  }

  &:focus {
    border-color: ${blue[400]};
    box-shadow: 0 0 0 3px ${theme.palette.mode === 'dark' ? blue[700] : blue[200]};
  }

  &:focus-visible {
    outline: 0;
  }
`,
);

const StyledButton = styled('button')(
    ({ theme }) => `
  font-family: 'IBM Plex Sans', sans-serif;
  font-size: 0.875rem;
  box-sizing: border-box;
  line-height: 1.5;
  border: 1px solid;
  border-radius: 999px;
  border-color: ${theme.palette.mode === 'dark' ? grey[800] : grey[200]};
  background: ${theme.palette.mode === 'dark' ? grey[900] : grey[50]};
  color: ${theme.palette.mode === 'dark' ? grey[200] : grey[900]};
  width: 32px;
  height: 32px;
  display: flex;
  flex-flow: row nowrap;
  justify-content: center;
  align-items: center;
  transition-property: all;
  transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  transition-duration: 120ms;

  &:hover {
    cursor: pointer;
    background: ${theme.palette.mode === 'dark' ? blue[700] : blue[500]};
    border-color: ${theme.palette.mode === 'dark' ? blue[500] : blue[400]};
    color: ${grey[50]};
  }

  &:focus-visible {
    outline: 0;
  }

  &.increment {
    order: 1;
  }
`,
);