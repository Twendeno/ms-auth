package com.twendeno.msauth.validation;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void sendEmail(Validation validation){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("no-reply@twendeno.com");
        mail.setTo(validation.getUser().getEmail());
        mail.setSubject("Twendeno - Email Verification");

        String message = String.format("Hello %s, \n\n" +
                "Thank you for signing up with Twendeno. \n\n" +
                "Your verification code is: %s \n\n" +
                "This code will expire in 10 minutes. \n\n" +
                "Thank you, \n" +
                "Twendeno Team", validation.getUser().getName(), validation.getCode());

        mail.setText(message);

        javaMailSender.send(mail);

    }
}
