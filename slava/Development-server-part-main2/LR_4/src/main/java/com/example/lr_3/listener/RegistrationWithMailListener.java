package com.example.lr_3.listener;

import com.example.lr_3.event.OnRegistrationEvent;
import com.example.lr_3.service.UserRegService;
import com.example.lr_3.service.VerificationService;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@PropertySource("classpath:mail.properties")
@Component
public class RegistrationWithMailListener {
    @Value("${mail.username}")
    private String from;
    private final JavaMailSender javaMailSender;
    private final UserRegService userRegService;
    private final VerificationService verificationService;
    public RegistrationWithMailListener(JavaMailSender javaMailSender,
                                        UserRegService userRegService,
                                        VerificationService verificationService) {
        this.javaMailSender = javaMailSender;
        this.userRegService = userRegService;
        this.verificationService = verificationService;
    }
    //@Transactional - аннотация на уровне и класса метода, которая позволяет отменить
    //внесенные изменения базы данных в случае возникновения ошибки
    @Transactional
    @SneakyThrows
    @EventListener
    public void onApplicationEvent(OnRegistrationEvent event) {
        verificationService.createVerificationToken(event.getRegDto().getId());
        String verTokenValue = verificationService.getVerificationTokenValueByUserId(event.getRegDto().getId());
        sendMail(verTokenValue, event);
    }
    private void sendMail(String verificationTokenValue, OnRegistrationEvent event){
        String subject = "Registration Confirmation";
        String message = "Registration Success";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(event.getRegDto().getEmail());
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(message + "\nconfirmation token: " + verificationTokenValue);
        simpleMailMessage.setFrom(from);
        javaMailSender.send(simpleMailMessage);
    }
}
