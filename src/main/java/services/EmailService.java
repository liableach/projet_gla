package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendTicketQR(String toEmail, File qrFile, String ticketId) {

        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Your Ticket QR Code");
            helper.setText("Here is your ticket QR. Ticket ID: " + ticketId);

            helper.addAttachment("ticket.png", qrFile);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Email failed", e);
        }
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
