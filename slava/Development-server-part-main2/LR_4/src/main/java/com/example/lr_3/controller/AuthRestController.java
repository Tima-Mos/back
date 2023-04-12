package com.example.lr_3.controller;

import com.example.lr_3.dto.AuthDto;
import com.example.lr_3.dto.RegDto;
import com.example.lr_3.entity.Token;
import com.example.lr_3.event.OnAuthorizationEvent;
import com.example.lr_3.event.OnRegistrationEvent;
import com.example.lr_3.service.UserAuthService;
import com.example.lr_3.service.UserRegService;
import com.example.lr_3.service.VerificationService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthRestController {
    private final UserAuthService userAuthService;
    private final UserRegService userRegService;
    private final VerificationService verificationService;
    private final ApplicationEventPublisher applicationEventPublisher;
    public AuthRestController(UserAuthService userAuthService,
                              UserRegService userRegService,
                              ApplicationEventPublisher applicationEventPublisher, VerificationService verificationService) {
        this.userAuthService = userAuthService;
        this.userRegService = userRegService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.verificationService = verificationService;
    }
    @PostMapping(path = "/login")
    @RequestMapping(value = "/login")
    public Token authorization(@RequestBody @Validated AuthDto authDto) throws Exception {
        applicationEventPublisher
                .publishEvent(new OnAuthorizationEvent(authDto));
        return userAuthService.authorization(authDto);
    }
    @PostMapping
    @RequestMapping("/reg")
    public Token registration(@RequestBody @Validated RegDto user) throws Exception {
        Token token = userRegService.registration(user);
        user.setId(token.getUserId());
        applicationEventPublisher.publishEvent(new OnRegistrationEvent(user));
        return token;
    }
    @PostMapping("/confirm")
    public void confirm(@RequestParam("token") String token){
        verificationService.confirm(token);
    }
}
