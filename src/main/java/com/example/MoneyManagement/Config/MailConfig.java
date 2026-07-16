package com.example.MoneyManagement.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import java.util.Properties;

@Slf4j
@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    //smtp properties

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean startTls;

    @Value("${spring.mail.default-encoding}")
    private String defaultEncoding;

    @Bean
    public JavaMailSender javaMailSender(){
        log.info("Initializing  javaMailSender");

        JavaMailSenderImpl   mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        mailSender.setDefaultEncoding(defaultEncoding);

        Properties properties = mailSender.getJavaMailProperties();

        properties.put("mail.smtp.auth", smtpAuth);
        properties.put("mail.smtp.starttls.enable", startTls);
        log.info("JavaMailSender Configured Successfully");

        return mailSender;
    }
}
