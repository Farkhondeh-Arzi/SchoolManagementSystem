package com.example.sm.mail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class Mail {
//    private final String PORT = "587";
//    private final String HOST = "smtp.gmail.com";
//    private final String USERNAME = "example.mail.receiver@gmail.com";
//    private final String PASSWORD = "kdjcxhwsgljwfpjb";
//    private final String EMAIL = "example.mail.receiver@gmail.com";

//    private final boolean AUTH = true;
//    private final boolean STARTTLS = true;

    @Autowired
    JavaMailSender mailSender;

    public void send(SubscribeForm subscriber, String filePath) throws MessagingException {
        MimeMessage msg = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//        helper.setSentDate(new Date());
        helper.setSubject("Users");
        helper.setTo(subscriber.getEmail());
        helper.setText("", true);
//        helper.setRecipients(Message.RecipientType.TO, InternetAddress.parse(subscriber.getEmail()));

        helper.addAttachment("export.zip", new ClassPathResource(filePath));

        mailSender.send(msg);
    }
}

