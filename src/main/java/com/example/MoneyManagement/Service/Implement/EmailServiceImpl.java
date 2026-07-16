//package com.example.MoneyManagement.Service.Implement;
//
//import com.example.MoneyManagement.Entity.UserEntity;
//import com.example.MoneyManagement.Service.EmailService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class EmailServiceImpl implements EmailService {
//
//    private final JavaMailSender mailSender;
//
//    private static final String FROM_EMAIL = "your_email@gmail.com";
//    private static final String APP_NAME = "Money Management App";
//
//
//    @Override
//    public void sendWelcomeEmail(String to, String name) {
//
//        try {
//            String subject = "Welcome to " + APP_NAME;
//            String body = "Hi " + name + ",\n\n"
//                    + "Welcome to " + APP_NAME + " 🎉\n"
//                    + "We are happy to have you onboard.\n\n"
//                    + "Regards,\n"
//                    + APP_NAME;
//
//            sendSimpleEmail(to, subject, body);
//
//            log.info("Welcome email sent to {}", to);
//
//        } catch (Exception e) {
//            log.error("Failed to send welcome email to {}", to, e);
//            throw new RuntimeException("Failed to send welcome email");
//        }
//    }
//
//
//    @Override
//    public void sendVerificationEmail(String to, String verificationLink) {
//
//        try {
//            String subject = "Verify Your Account - " + APP_NAME;
//            String body = "Please verify your account by clicking below link:\n\n"
//                    + verificationLink + "\n\n"
//                    + "This link will expire soon.\n\n"
//                    + "Regards,\n"
//                    + APP_NAME;
//
//            sendSimpleEmail(to, subject, body);
//
//            log.info("Verification email sent to {}", to);
//
//        } catch (Exception e) {
//            log.error("Failed to send verification email to {}", to, e);
//            throw new RuntimeException("Failed to send verification email");
//        }
//    }
//
//    /**
//     * Send Password Reset Email
//     */
//    @Override
//    public void sendPasswordResetEmail(String to, String resetLink) {
//
//        try {
//            String subject = "Password Reset Request - " + APP_NAME;
//            String body = "You requested a password reset.\n\n"
//                    + "Click below link to reset your password:\n\n"
//                    + resetLink + "\n\n"
//                    + "If you did not request this, please ignore this email.\n\n"
//                    + "Regards,\n"
//                    + APP_NAME;
//
//            sendSimpleEmail(to, subject, body);
//
//            log.info("Password reset email sent to {}", to);
//
//        } catch (Exception e) {
//            log.error("Failed to send password reset email to {}", to, e);
//            throw new RuntimeException("Failed to send password reset email");
//        }
//    }
//
//    @Override
//    public void sendReminderEmail(UserEntity user) {
//
//    }
//
//    /**
//     * Common method for sending simple emails
//     */
//    private void sendSimpleEmail(String to, String subject, String body) {
//
//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        message.setFrom(FROM_EMAIL);
//
//        mailSender.send(message);
//    }
//}


package com.example.MoneyManagement.Service.Implement;

import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Service.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private static final String FROM_EMAIL = "your_email@gmail.com";
    private static final String APP_NAME = "Money Management App";

    @Override
    public void sendWelcomeEmail(String to, String name) {
        try {
            String subject = "Welcome to " + APP_NAME;
            String body =
                    "Hi " + name + ",\n\n" +
                            "Welcome to " + APP_NAME + ".\n\n" +
                            "We are happy to have you onboard.\n\n" +
                            "Regards,\n" +
                            APP_NAME;
            sendSimpleEmail(to, subject, body);
            log.info("Welcome email sent to {}", to);

        } catch (Exception e) {
            log.error("Failed to send welcome email", e);
            throw new RuntimeException("Unable to send welcome email");
        }
    }
    @Override
    public void sendVerificationEmail(String to, String verificationLink) {

        try {
            String subject = "Verify Your Account";
            String body =
                    "Hello,\n\n" +
                            "Please verify your account.\n\n" +
                            verificationLink +
                            "\n\nRegards,\n" +
                            APP_NAME;

            sendSimpleEmail(to, subject, body);
            log.info("Verification email sent to {}", to);
        } catch (Exception e) {
            log.error("Verification email failed", e);
            throw new RuntimeException("Unable to send verification email");
        }
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetLink) {
        try {
            String subject = "Password Reset";
            String body =
                    "Hello,\n\n" +
                            "Click the link below to reset your password.\n\n" +
                            resetLink +
                            "\n\nRegards,\n" +
                            APP_NAME;

            sendSimpleEmail(to, subject, body);
            log.info("Password reset email sent.");
        } catch (Exception e) {
            log.error("Password reset email failed", e);
            throw new RuntimeException("Unable to send password reset email");
        }
    }

    @Override
    public void sendReminderEmail(UserEntity user) {
        try {
            String subject = "Daily Expense & Income Reminder";
            String body =
                    "Hello " + user.getName() + ",\n\n" +
                            "This is your daily reminder from Money Management App.\n\n" +
                            "Please update today's:\n\n" +
                            "• Income\n" +
                            "• Expense\n" +
                            "• Categories\n\n" +
                            "Keeping your transactions updated every day helps you track your finances better.\n\n" +
                            "Regards,\n" +
                            APP_NAME;

            sendSimpleEmail(
                    user.getEmail(),
                    subject,
                    body
            );
            log.info("Reminder email sent to {}", user.getEmail());

        } catch (Exception e) {
            log.error("Reminder email failed", e);
        }

    }

    @Override
    public void sendDailyReportEmail(UserEntity user,
                                     byte[] pdfBytes) {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(
                            mimeMessage,
                            true
                    );
            helper.setFrom(FROM_EMAIL);

            helper.setTo(user.getEmail());

            helper.setSubject("Daily Expense & Income Report");
            helper.setText(
                    "Hello " + user.getName() + ",\n\n"
                            + "Please find attached your Daily Money Management Report.\n\n"

                            + "The report contains:\n"

                            + "• All Income\n"

                            + "• All Expenses\n"

                            + "• Categories\n"

                            + "• Total Income\n"

                            + "• Total Expense\n"

                            + "• Savings\n\n"

                            + "Regards,\n"

                            + APP_NAME

            );
            helper.addAttachment(
                    "MoneyManagementReport.pdf",
                    new ByteArrayResource(pdfBytes)
            );

            mailSender.send(mimeMessage);

            log.info("PDF Report email sent successfully to {}",
                    user.getEmail());

        }

        catch (Exception e) {

            log.error("Unable to send PDF report email", e);

            throw new RuntimeException("Unable to send report email");

        }

    }
    private void sendSimpleEmail(
            String to,
            String subject,
            String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(FROM_EMAIL);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);

    }

}