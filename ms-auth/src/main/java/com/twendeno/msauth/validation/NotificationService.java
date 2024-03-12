package com.twendeno.msauth.validation;

import com.twendeno.msauth.license.License;
import com.twendeno.msauth.user.User;
import com.twendeno.msauth.userLicense.UserLicense;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@AllArgsConstructor
@Service
public class NotificationService {

    public static final String NO_REPLY_TWENDENO_COM = "no-reply@twendeno.com";
    private final JavaMailSender javaMailSender;

    public void sendEmail(Validation validation){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(NO_REPLY_TWENDENO_COM);
        mail.setTo(validation.getUser().getEmail());
        mail.setSubject("TWENDENO - Email Verification");

        String message = String.format("""
                Hello %s,\s

                Your verification code is: %s\s

                This code will expire in 10 minutes.\s

                Thank you,\s
                Twendeno Team""", validation.getUser().getName(), validation.getCode());

        mail.setText(message);

        javaMailSender.send(mail);

    }

    public void sendActivationEmail(UserLicense userLicense){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(NO_REPLY_TWENDENO_COM);
        mail.setTo(userLicense.getUser().getEmail());
        mail.setSubject("TWENDENO - License Activation");

        // Parse the date-time string
        ZonedDateTime dateTime = ZonedDateTime.parse(userLicense.getExpiration().toString(), DateTimeFormatter.ISO_DATE_TIME);

        // Create a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd 'at' HH:mm", Locale.ENGLISH);

        // Format the date-time
        String formattedDateTime = dateTime.format(formatter);

        String message = String.format("""
                        Hello %s,\s

                        Thank you for activating your licence.\s

                        It is valid for %s month(s), it expires %s.\s

                        Here is the information for the machine on which you activated your license\s

                        %s
                        Please contact the support team if you are not the originator of this action.

                        Twendeno Team""",
                userLicense.getUser().getName(),
                userLicense.getLicense().getDuration(),
                formattedDateTime,
                this.getMachineInfo()
        );

        mail.setText(message);

        javaMailSender.send(mail);

    }

    public void sendMailWithAttachment(User user, String key, License license){

        MimeMessagePreparator preparator = mimeMessage -> {
            try {

                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8"); // Activer le mode multipart

                helper.setFrom(NO_REPLY_TWENDENO_COM);
                helper.setTo(user.getEmail());
                helper.setSubject("TWENDENO - License Key");

                String message = String.format("""
                        Hello %s,\s

                        Thank you for subscribing to the %s license\s

                        Your licence key is on the attached file\s

                        Please communicate this to anyone\s

                        Thank you,\s
                        Twendeno Team""", user.getName(),license.getName());

                helper.setText(message);

                ByteArrayResource inputStream = new ByteArrayResource(key.getBytes(StandardCharsets.UTF_8));
                helper.addAttachment("license.txt", inputStream,"text/plain");


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        try {
            javaMailSender.send(preparator);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMachineInfo(){
        StringBuilder sb = new StringBuilder();
        try {
            InetAddress ip = InetAddress.getLocalHost();
            sb.append("Host Name: ").append(ip.getHostName()).append("\n");
            sb.append("IP Address: ").append(ip.getHostAddress()).append("\n");
            sb.append("Os: ").append(System.getProperty("os.name")).append("\n");
            System.out.println(ip.toString());

        }catch (UnknownHostException e){
            throw new RuntimeException(e);
        }

        return sb.toString();
    }
}
