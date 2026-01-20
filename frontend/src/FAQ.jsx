import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import Typography from '@mui/material/Typography';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';

export default function FAQ() {
  return (
   <div>
  <Accordion sx={{backgroundColor:"#0059B2", color:"white"}}>
    <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
      <Typography component="span">Koje je vrijeme prijave i odjave?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        Prijava (Check-in) je moguća od 14:00h, a odjava (Check-out) se mora obaviti do 10:00h.
      </Typography>
    </AccordionDetails>
  </Accordion>

  <Accordion sx={{backgroundColor:"#0059B2", color:"white"}}>
    <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
      <Typography component="span">Je li doručak uključen u cijenu?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        Doručak je uključen u cijenu svih rezervacija i poslužuje se u restoranu od 07:00 do 10:30h.
      </Typography>
    </AccordionDetails>
  </Accordion>

  <Accordion sx={{backgroundColor:"#0059B2", color:"white"}}>
    <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
      <Typography component="span">Imate li osiguran parking?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        Da, nudimo besplatan parking za sve goste hotela koji se nalazi neposredno uz objekt.
      </Typography>
    </AccordionDetails>
  </Accordion>

  <Accordion sx={{backgroundColor:"#0059B2", color:"white"}}>
    <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
      <Typography component="span">Jesu li kućni ljubimci dopušteni?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        Kućni ljubimci su dopušteni uz prethodnu najavu i nadoplatu od 15 € po noćenju.
      </Typography>
    </AccordionDetails>
  </Accordion>

  <Accordion sx={{backgroundColor:"#0059B2", color:"white"}}>
    <AccordionSummary expandIcon={<ArrowDownwardIcon />}>
      <Typography component="span">Nudite li uslugu prijevoza iz zračne luke?</Typography>
    </AccordionSummary>
    <AccordionDetails>
      <Typography>
        Da, organiziramo prijevoz iz zračne luke uz nadoplatu. Molimo da nas kontaktirate 24h ranije.
      </Typography>
    </AccordionDetails>
  </Accordion>
</div>
  );
}
