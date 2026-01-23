package com.juliusmeinl.backend.service;

import com.juliusmeinl.backend.dto.RezervacijaRequestDTO;
import com.juliusmeinl.backend.model.Rezervacija;
import com.juliusmeinl.backend.repository.RezervacijaRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final RezervacijaRepository rezervacijaRepository;

    public EmailService(JavaMailSender mailSender,
                        RezervacijaRepository rezervacijaRepository) {
        this.mailSender = mailSender;
        this.rezervacijaRepository = rezervacijaRepository;
    }

    public void posaljiRezervacijuMail(Integer rezervacijaId, RezervacijaRequestDTO rezervacijaRequestDTO) {

        Rezervacija rezervacija = rezervacijaRepository.findById(rezervacijaId)
                .orElseThrow(() -> new RuntimeException("Rezervacija ne postoji"));

        String poruka = """
                Poštovani,

                Vaša rezervacija je uspješno zaprimljena.

                Vaše rezervacije:\s
                %s
               \s
                Ukupan iznos: %s €

                Radujemo se Vašem dolasku!
                Modrila Hotel
               \s""".formatted(
                rezervacijaRequestDTO.printForEmail(),
                rezervacija.getIznosRezervacije()
        );

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(rezervacija.getKorisnik().getEmail());
        message.setSubject("Potvrda rezervacije");
        message.setText(poruka);

        mailSender.send(message);
    }
}

