package com.juliusmeinl.backend.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private JavaMailSender emailSender;

    @PostMapping("/send")
    public void sendEmail(@RequestBody EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("juliusmeinlt3.3@gmail.com");
        message.setTo(emailRequest.getTo());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());
        emailSender.send(message);
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EmailRequest {
    private String to;
    private String subject;
    private String body;
}
