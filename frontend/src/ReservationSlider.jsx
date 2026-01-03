import React, { useEffect, useState } from "react";
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Reservation from './Reservation';
import { colors } from '@mui/material';
import { Link } from 'react-router-dom';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import dayjs from 'dayjs';
import { DemoContainer } from '@mui/x-date-pickers/internals/demo';
import axios from "axios";

const steps = ['Datum i broj gostiju', 'Odabir dostupne sobe', 'Dodatni sadržaji'];

export default function HorizontalLinearStepper() {
  const [activeStep, setActiveStep] = React.useState(0);
  const [skipped, setSkipped] = React.useState(new Set());
  const[datumDolaska,setDatumDolaska]=React.useState(dayjs().startOf('day'));
  const[datumDanas,setDatumDanas]=React.useState(dayjs().startOf('day'));
  const[datumOdlaska,setDatumOdlaska]=React.useState(dayjs().add(1, 'day'));
  const[slobodneSobe,setSlobodneSobe]=React.useState(null);
  const[odabranoSoba,setOdabranoSoba]=React.useState([0]);
  const[totalOdabranih,setTotalOdabranih]=React.useState(0);

 useEffect(() => {
  if(activeStep==1){
    axios
      .get(`${import.meta.env.VITE_API_URL}` + '/dates', { withCredentials: true })
      .then((responseSoba) => {
        setSlobodneSobe(responseSoba.data);
        setOdabranoSoba(new Array(responseSoba.data.length).fill(0) )
      })
      .catch((error) => {console.log("myb nije povezano ");;
    setSlobodneSobe(null)});
    
  }}, [activeStep]);
  

  const isStepOptional = (step) => {
    return step === 2;
  };

  const isStepSkipped = (step) => {
    return skipped.has(step);
  };

  async function postDates(datumDolaska, datumOdlaska){
    const datumi={
      datumDolaska,
      datumOdlaska
    }
       try {
                  return await axios.post(`${import.meta.env.VITE_API_URL}` + '/dates', datumi,  {withCredentials: true} )
                  
                  
              } catch (error) {
                  console.error('Error: nije se poslao post zbog necega', error.response?.data)
              }

    }
  
 
    
  const handleNext =  () => {
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
      if(datumDolaska.isSame(datumOdlaska)){
      alert("Datum dolaska ne može biti jednak datumu odlaska!");
      return;
      }
     postDates(datumDolaska.format('DD.MM.YYYY'), datumOdlaska.format('DD.MM.YYYY')); 
    }
    if(activeStep===1){
      {/*potrebno ograničenje u broju soba */}
      if(totalOdabranih>5){
        alert("Nažalost, nije moguće rezervirati više o 5 soba.");
        return;
      }
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
content=slobodneSobe.map((soba,i)=>( 
<div style={{
              border: "1px solid gray",
              padding: "8px",
              marginBottom: "5px",
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <div style={{ flexGrow: 1, paddingRight: "15px" }}>
              <div>Naziv sobe: {soba.vrsta}</div>
              {soba.balkon && <div>Balkon</div>}
              {soba.pogledNaMore && <div>Pogled na more</div>}
              <div>Cijena: {soba.cijena} €</div>
              <NumberInput value={odabranoSoba[i]||0} onChange={(event,val)=>{
                if(val>soba.dostupno){
                  alert("Nije dostupno toliko soba te vrste");
                  return;
                }
                const dodano=[...odabranoSoba];
                dodano[i]=val;
                let zb=0;
                for(let ii=0;ii<dodano.length;ii++){
                  if(dodano[ii]){
                    zb+=dodano[ii];
                  }
                }
                setTotalOdabranih(zb);
                setOdabranoSoba(dodano)}} min={0} max={5} defaultValue={0}  />
            </div>
            <div style={{ flexShrink: 0 }}></div>
              </div>))
  }
  else{
   content= <span>Nema odgovarajućih soba na taj datum</span>
  }
  
} else {
  content = <span>3. korak</span>;
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
            <Button onClick={handleNext} variant="contained" style={{color: "#e4e8ecff"}}>
              {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
            </Button>
          </Box>
        </React.Fragment>
      )}
    </Box>
  );
}