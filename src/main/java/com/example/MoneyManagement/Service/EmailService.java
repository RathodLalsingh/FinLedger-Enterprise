//package com.example.MoneyManagement.Service;
//
//
//import com.example.MoneyManagement.Entity.UserEntity;
//
//public interface EmailService {
//
//    void sendWelcomeEmail(String to, String name);
//    void sendVerificationEmail(String to, String verificationLink);
//
//    void sendPasswordResetEmail(String to, String resetLink);
//
//    void sendReminderEmail(UserEntity user);
//}

package com.example.MoneyManagement.Service;

import com.example.MoneyManagement.Entity.UserEntity;

public interface EmailService {

    void sendWelcomeEmail(String to, String name);
    void sendVerificationEmail(String to, String verificationLink);
    void sendPasswordResetEmail(String to, String resetLink);
    void sendReminderEmail(UserEntity user);
    void sendDailyReportEmail(UserEntity user,
                              byte[] pdfBytes);
}