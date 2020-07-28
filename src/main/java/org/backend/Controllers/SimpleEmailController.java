package org.backend.Controllers;


import javax.mail.internet.MimeMessage;

import org.backend.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleEmailController {

    EmailService emailService;

    public SimpleEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/send_email")
    @ResponseBody
    String home() {
        try {
            emailService.sendEmail();
            return "Email Sent!";
        } catch (Exception ex) {
            return "Error in sending email: " + ex;
        }
    }


}
