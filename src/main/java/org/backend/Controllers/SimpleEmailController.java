package org.backend.Controllers;

import org.backend.DTOs.EmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.Arrays;

@RestController
public class SimpleEmailController {

    @Autowired
    private JavaMailSender sender;

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
          return "Error in sending email:" +Arrays.toString(e.getStackTrace());
        }

    }
}