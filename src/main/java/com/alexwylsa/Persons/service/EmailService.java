package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.exceptions.FileStorageException;
import com.alexwylsa.Persons.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private StaffRepo staffRepo;

    public String sendSimpleEmail(User requester, Long toUserId, Long staffIdFrom, Long staffIdTo){

        Staff staffFrom = staffRepo.findById(staffIdFrom).get();
        String staffMailFrom =staffFrom.getMail();

        Staff staffTo = staffRepo.findById(staffIdTo).get();
        String staffMailTo = staffTo.getMail();

        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(staffMailFrom);
        message.setTo(staffMailTo);
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

