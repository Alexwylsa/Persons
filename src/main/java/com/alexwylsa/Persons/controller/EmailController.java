package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class EmailController {

    @Autowired
    EmailService emailService;

    @ResponseBody
    @RequestMapping("/sendSimpleEmail")
    public String sendSimpleEmail(@AuthenticationPrincipal User requester,
                                  @RequestParam Long toUserId ,
                                  @RequestParam Long staffIdFrom,
                                  @RequestParam Long staffIdTo,
                                  @RequestParam String subject,
                                  @RequestParam String text) {

        return emailService.sendSimpleEmail(requester, toUserId, staffIdFrom, staffIdTo, subject, text);
    }
}
