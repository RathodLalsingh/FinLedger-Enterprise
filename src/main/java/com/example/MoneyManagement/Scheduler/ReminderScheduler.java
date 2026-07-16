
package com.example.MoneyManagement.Scheduler;

import com.example.MoneyManagement.Entity.UserEntity;
import com.example.MoneyManagement.Repository.UserRepository;
import com.example.MoneyManagement.Service.EmailService;
import com.example.MoneyManagement.Service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReminderScheduler {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ReportService reportService;

   @Scheduled(cron="*/30 * * * * *")
    public void sendDailyReminder() {

        log.info("================================================");
        log.info("Daily Reminder Scheduler Started");
        log.info("================================================");
        List<UserEntity> users = userRepository.findAll();

        if (users.isEmpty()) {
            log.info("No users found.");
            return;
        }
        for (UserEntity user : users) {
            try {
                log.info("Generating report for {}",
                        user.getEmail());
                byte[] pdf =
                        reportService.generateDailyReport(user);
                emailService.sendReminderEmail(user);
                emailService.sendDailyReportEmail(user, pdf);
                log.info("Email sent successfully to {}",
                        user.getEmail());
            }
            catch (Exception e) {
                log.error("Unable to send email to {}",
                        user.getEmail(),
                        e);
            }
        }
        log.info("================================================");
        log.info("Daily Reminder Scheduler Finished");
        log.info("================================================");

    }

}