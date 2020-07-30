package org.backend.Controllers;


import javax.mail.internet.MimeMessage;

import org.backend.DTOs.EmailDTO;
import org.backend.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class SimpleEmailController {

    EmailService emailService;
    @Autowired
    private JavaMailSender sender;

    public SimpleEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/send_email")
    String home() {
        try {
            emailService.sendEmail();
            return "Email Sent!";
        } catch (Exception ex) {
            return "Error in sending email: " + ex;
        }
    }

    @PostMapping("/contact")
    private String sendEmail(@RequestBody EmailDTO emailDTO) throws Exception {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(emailDTO.getEmail());
            helper.setText(emailDTO.getMessage());
            helper.setSubject(emailDTO.getSubject());

            sender.send(message);
            return "Email Sent!";
        } catch (Exception e) {
            return "Error in sending email:" + Arrays.toString(e.getStackTrace());
        }

    }


}



