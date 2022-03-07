package com.example.sm.schedule;

import com.example.sm.excel.ExcelExporter;
import com.example.sm.mail.Mail;
import com.example.sm.mail.SubscribeForm;
import com.example.sm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.IOException;

@Component
public class ScheduleEmailing {

    @Autowired
    UserService userService;

    @Autowired
    Mail mail;

//    @Scheduled(cron = "0 48 18 * * SUN")
    @Scheduled(fixedDelay = 3000L)
    public void sendEmail() {

        System.out.println("sending mail...");

        SubscribeForm subscribeForm = new SubscribeForm();
        subscribeForm.setName("ADMIN");
        subscribeForm.setEmail("example.for.network@gmail.com");

        try {

            ExcelExporter exporter = new ExcelExporter(userService.getAll());
            exporter.export();

            mail.send(subscribeForm, "export.zip");
            System.out.println("mail sent successfully");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }
}
