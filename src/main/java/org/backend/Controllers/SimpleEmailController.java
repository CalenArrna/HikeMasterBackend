package org.backend.Controllers;

import org.backend.DTOs.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@RestController
public class SimpleEmailController {

    @Autowired
    private JavaMailSenderImpl sender;

    @PostMapping("/contact")
    private String sendEmail(@RequestBody EmailDTO emailDTO) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("hikemasteradm@gmail.com");
            helper.setTo("hikemasteradm@gmail.com");
            helper.setText("Email-address: " +emailDTO.getEmail()+" Message: "+emailDTO.getMessage());
            helper.setSubject(emailDTO.getSubject());
            sender.send(message);
            return "Email Sent!";
        } catch (MessagingException e) {
           return "Error: "+Arrays.toString(e.getStackTrace()) ;
        }



    }
}