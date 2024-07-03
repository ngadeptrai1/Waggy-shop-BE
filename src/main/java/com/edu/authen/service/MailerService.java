package com.edu.authen.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class MailerService  {
    private final static Logger LOGGER = LoggerFactory
            .getLogger(MailerService.class);

  private final JavaMailSender mailSender;
    @Async
   public void sendEmail(String to, String body) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(body, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("ngadeptrai");
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            LOGGER.error("failed to send email", e);
            throw new IllegalStateException("failed to send email");
        }
   }

}

