package projectbms;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    private final JavaMailSender mailSender;
    
    org.apache.logging.log4j.Logger logger = LogManager.getLogger(SendEmail.class);

    @Autowired
    public SendEmail(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String toEmail, String subject, String body, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("inventorymanagementsystem2023@gmail.com");
            helper.setTo(toEmail);
            helper.setText(body);
            helper.setSubject(subject);

            // Attach PDF file
            Path path = Paths.get(filePath);
            byte[] pdfBytes = Files.readAllBytes(path);
            ByteArrayResource resource = new ByteArrayResource(pdfBytes);
            helper.addAttachment(filePath.replace("C:\\IMS3\\Bills\\", ""), resource);

            mailSender.send(message);
            logger.info("Mail sent successfully");
        } catch (MessagingException | IOException e) {
            logger.error("Failed to send email: " + e.getMessage());
        }
    }
}
