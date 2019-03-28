package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.Constans.MyConstants;
import com.alexwylsa.Persons.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.http.HTTPException;

@RestController
public class EmailController {

    @Autowired
    @Qualifier("mailBean")
    private JavaMailSender emailSender;



    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendSimpleEmail() {

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(MyConstants.FRIEND_EMAIL);
        message.setSubject("Test Simple Email");
        message.setText("Hello, Im testing Simple Email");

        // Send Message!
        try {
            this.emailSender.send(message);
            return "Email Sent!";
        }
        catch (Exception ex) {
            throw new FileStorageException("Sending failed", ex);
        }




    }

}
