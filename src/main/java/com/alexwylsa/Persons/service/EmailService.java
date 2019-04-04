package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.exceptions.FileStorageException;
import com.alexwylsa.Persons.repo.StaffRepo;
import com.alexwylsa.Persons.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private StaffRepo staffRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    ExecutorService executorService;

    public String sendSimpleEmail(User requester, Long toUserId, Long staffIdFrom, Long staffIdTo, String subject, String text){


        Staff staffFrom = staffRepo.findById(staffIdFrom).get();
        String staffMailFrom =staffFrom.getMail();

        Staff staffTo = staffRepo.findById(staffIdTo).get();
        String staffMailTo = staffTo.getMail();

        StringBuilder sb = new StringBuilder();
        sb.append("You have message from ")
                .append(staffFrom.getFirstName())
                .append(" ").append(staffFrom.getLastName()).append("\n")
                .append(text);


        // Create a Simple MailMessage.
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(staffMailFrom);
        message.setTo(staffMailTo);
        message.setSubject(subject);
        message.setText(sb.toString());

        // Send Message!
        try {
            executorService.submit(()->this.emailSender.send(message));


            return "Email Sent!";
        }
        catch (Exception ex) {
            throw new FileStorageException("Sending failed", ex);
        }
    }

    }

